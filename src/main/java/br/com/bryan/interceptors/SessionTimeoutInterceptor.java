package br.com.bryan.interceptors;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionTimeoutInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
        Long lastInteraction = (Long) session.get("LAST_INTERACTION_TIMESTAMP_MS");
        Integer maxInactiveInterval = (Integer) session.get("MAX_INACTIVE_INTERVAL_MS");

        long currentTime = System.currentTimeMillis();

        if (lastInteraction != null && maxInactiveInterval != null) {
            long timeSinceLastInteraction = currentTime - lastInteraction;

            if (timeSinceLastInteraction > maxInactiveInterval) {
                session.clear();
                return "sessionTimeout";
            }
        }

        session.put("LAST_INTERACTION_TIMESTAMP_MS", currentTime);
        
        return invocation.invoke();
	}

}
