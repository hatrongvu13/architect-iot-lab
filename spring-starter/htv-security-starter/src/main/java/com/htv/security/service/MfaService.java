package com.htv.security.service;

import com.htv.security.HtvSecurityProperties;
import com.htv.security.model.AuthenticatedUser;

public interface MfaService {
    MfaSetup beginSetup(AuthenticatedUser user, HtvSecurityProperties.MfaMethod method);
    boolean confirmSetup(String setupId, String code);
    MfaChallenge createChallenge(AuthenticatedUser user, HtvSecurityProperties.MfaMethod method);
    MfaVerification verify(String challengeId, String code);

    record MfaSetup(HtvSecurityProperties.MfaMethod method, String setupId, String qrUri, String qrImageBase64, String emailHint, long expiresInSeconds){}
    record MfaChallenge(String challengeId, HtvSecurityProperties.MfaMethod method, String deliveryHint, long expiresInSeconds){}
    record MfaVerification(boolean success, String userId){}
}
