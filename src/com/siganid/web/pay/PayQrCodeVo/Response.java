
package com.siganid.web.pay.PayQrCodeVo;

public class Response {
    String qr_id;
    String qr_url;
    String qr_code;
    String qr_type;

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getQr_id() {
        return qr_id;
    }

    public String getQr_url() {
        return qr_url;
    }

    public String getQr_type() {
        return qr_type;
    }

    public void setQr_id(String qr_id) {
        this.qr_id = qr_id;
    }

    public void setQr_type(String qr_type) {
        this.qr_type = qr_type;
    }

    public void setQr_url(String qr_url) {
        this.qr_url = qr_url;
    }

}
