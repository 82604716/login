
package com.siganid.web.pay.PayResult;

import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Response {

    private Integer totalResults;
    private List<QrTrade> qrTrades = null;

    /**
     * 
     * @return
     *     The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * @param totalResults
     *     The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * 
     * @return
     *     The qrTrades
     */
    public List<QrTrade> getQrTrades() {
        return qrTrades;
    }

    /**
     * 
     * @param qrTrades
     *     The qr_trades
     */
    public void setQrTrades(List<QrTrade> qrTrades) {
        this.qrTrades = qrTrades;
    }

}
