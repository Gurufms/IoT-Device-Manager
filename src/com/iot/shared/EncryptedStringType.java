package com.iot.shared;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.hibernate.type.StandardBasicTypes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class EncryptedStringType implements UserType {

    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890abcdef1234567890abcdef"; 
    private static final String CHARSET = "UTF-8";

    @Override
    public int[] sqlTypes() {
        return new int[] { StandardBasicTypes.STRING.sqlType() };
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        String encryptedData = rs.getString(names[0]);
        if (encryptedData == null) {
            return null;
        }

        try {
            // Decrypt the password 
            return decrypt(encryptedData);
        } catch (Exception e) {
            throw new HibernateException("Error decrypting value", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, StandardBasicTypes.STRING.sqlType());
            return;
        }

        try {
            // Encrypt the string 
            String encryptedValue = encrypt((String) value);
            st.setString(index, encryptedValue);
        } catch (Exception e) {
            throw new HibernateException("Error encrypting value", e);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;  // Simple copy for encryption type
    }

    @Override
    public boolean isMutable() {
        return false;  // Encrypted field is not mutable
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    // AES encryption 
    private String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // AES decryption
    private String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, CHARSET);
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return (x == y) || (x != null && y != null && x.equals(y));
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return (x != null ? x.hashCode() : 0);
    }
}
