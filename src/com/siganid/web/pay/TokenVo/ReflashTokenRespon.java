
package com.siganid.web.pay.TokenVo;


public class ReflashTokenRespon {
    private String access_token;
    private Integer expires_in;
    private String token_type;
    private String scope;
    private String refresh_token;

    /**
     * @return The access_token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * @param access_token The access_token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * @return The expires_in
     */
    public Integer getExpires_in() {
        return expires_in;
    }

    /**
     * @param expires_in The expires_in
     */
    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    /**
     * @return The token_type
     */
    public String getToken_type() {
        return token_type;
    }

    /**
     * @param token_type The token_type
     */
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    /**
     * @return The scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope The scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return The refresh_token
     */
    public String getRefresh_token() {
        return refresh_token;
    }

    /**
     * @param refresh_token The refresh_token
     */
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

}
