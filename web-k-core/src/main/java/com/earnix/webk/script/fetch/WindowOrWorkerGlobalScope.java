package com.earnix.webk.script.fetch;

import com.earnix.webk.script.future.Promise;
import com.earnix.webk.script.web_idl.NewObject;
import com.earnix.webk.script.web_idl.Optional;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
public interface WindowOrWorkerGlobalScope {
     @NewObject
     Promise<Response> fetch(RequestInfo input, @Optional RequestInit init);
}
