package com.htv.security.support;

import com.htv.security.HtvSecurityProperties;
import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.MfaService;

public class DisableMfaService implements MfaService {
    @Override
    public MfaSetup beginSetup(AuthenticatedUser user, HtvSecurityProperties.MfaMethod method) {
        throw new IllegalStateException("MFA is disabled");
    }

    @Override
    public boolean confirmSetup(String setupId, String code) {
        return false;
    }

    @Override
    public MfaChallenge createChallenge(AuthenticatedUser user, HtvSecurityProperties.MfaMethod method) {
        throw new IllegalStateException("MFA is disabled");
    }

    @Override
    public MfaVerification verify(String challengeId, String code) {
        return new MfaVerification(false, null);
    }
}
