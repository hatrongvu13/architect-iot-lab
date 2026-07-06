package com.htv.security.service;

import com.htv.security.HtvSecurityProperties;

public interface UserMfaPreferenceService {
    void enableMfa(String userId, HtvSecurityProperties.MfaMethod method);
    void disableMfa(String userId);
}
