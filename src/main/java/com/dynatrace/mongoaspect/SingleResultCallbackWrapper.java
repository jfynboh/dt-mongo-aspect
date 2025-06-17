package com.dynatrace.mongoaspect;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.InProcessLink;
import com.dynatrace.oneagent.sdk.api.InProcessLinkTracer;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;
import com.mongodb.internal.async.SingleResultCallback;

public class SingleResultCallbackWrapper implements SingleResultCallback<Object> {
    private final InProcessLink link;
    private final SingleResultCallback<Object> delegate;
    private static final OneAgentSDK oneagentSdk = OneAgentSDKFactory.createInstance();

    public SingleResultCallbackWrapper(InProcessLink link, SingleResultCallback<Object> delegate) {
        this.link = link;
        this.delegate = delegate;
    }

    @Override
    public void onResult(Object server, Throwable throwable) {
        // System.out.println(" -- In SingleResultCallbackWrapper ");
        InProcessLinkTracer tracer = oneagentSdk.traceInProcessLink(link);
        try {
            tracer.start();
            delegate.onResult(server, throwable);
        } finally {
            tracer.end();
        }

    }

}
