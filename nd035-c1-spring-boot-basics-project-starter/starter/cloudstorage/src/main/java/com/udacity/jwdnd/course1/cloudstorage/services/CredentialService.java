package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    HashService hashService;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentialsListByUserId(int userId) {
        return credentialMapper.getCredentialsListByUserId(userId);
    }

    public int insertOrUpdateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodeSalt = Base64.getEncoder().encodeToString(salt);
        String encryptPassword = encryptionService.encryptValue(credential.getPassword(), encodeSalt);
        credential.setKey(encodeSalt);
        credential.setPassword(encryptPassword);
        if (credential.getCredentialId() == 0) {
            return credentialMapper.insertCredential(credential);
        }
        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredentialById(credentialId);
    }

    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }
}
