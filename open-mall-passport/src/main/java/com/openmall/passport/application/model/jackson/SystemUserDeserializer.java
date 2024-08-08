package com.openmall.passport.application.model.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.openmall.passport.application.model.SystemUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom Deserializer for {@link User} class. This is already registered with
 * {@link SystemUserMixin}. You can also use it directly with your mixin class.
 *
 * @author Jitendra Singh
 * @see SystemUserMixin
 * @since 4.2
 */
public class SystemUserDeserializer extends JsonDeserializer<SystemUserDetails> {

    private static final TypeReference<Set<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<Set<SimpleGrantedAuthority>>() {
    };

    /**
     * This method will create {@link User} object. It will ensure successful object
     * creation even if password key is null in serialized json, because credentials may
     * be removed from the {@link User} by invoking {@link User#eraseCredentials()}. In
     * that case there won't be any password key in serialized json.
     *
     * @param jp   the JsonParser
     * @param ctxt the DeserializationContext
     * @return the user
     * @throws IOException if a exception during IO occurs
     */
    @Override
    public SystemUserDetails deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
//        Set<SimpleGrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"), SIMPLE_GRANTED_AUTHORITY_SET);
        JsonNode passwordNode = readJsonNode(jsonNode, "password");
        Long userId = readJsonNode(jsonNode, "userId").asLong();
        String username = readJsonNode(jsonNode, "username").asText();
        String password = passwordNode.asText("");
        boolean enabled = readJsonNode(jsonNode, "enabled").asBoolean();
        boolean accountNonExpired = readJsonNode(jsonNode, "accountNonExpired").asBoolean();
        boolean credentialsNonExpired = readJsonNode(jsonNode, "credentialsNonExpired").asBoolean();
        boolean accountNonLocked = readJsonNode(jsonNode, "accountNonLocked").asBoolean();
        String email = readJsonNode(jsonNode, "email").asText();
        String nickname = readJsonNode(jsonNode, "nickname").asText();
        SystemUserDetails result = new SystemUserDetails(userId, username, password, new HashSet<>(), accountNonLocked, enabled, credentialsNonExpired, accountNonExpired, email, nickname);
        if (passwordNode.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
