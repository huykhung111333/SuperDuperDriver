package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("Insert Into CREDENTIALS(url, username, key, password, userid) Values(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);


    @Select("Select * From CREDENTIALS Where userid = #{userId}")
    List<Credential> getCredentialsListByUserId(int userId);

    @Update("Update CREDENTIALS Set url = #{url}, username = #{username}, key = #{key}, password = #{password} Where credentialId = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("Delete CREDENTIALS Where credentialid = #{credentialId}")
    int deleteCredentialById(int credentialId);

    @Select("Select * FROM CREDENTIALS Where credentialid = #{credentialId}")
    Credential getCredentialById(int credentialId);
}
