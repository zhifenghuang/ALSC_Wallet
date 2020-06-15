package com.alsc.alsc_wallet.bean.cold;

import java.util.List;

public class RequestWalletBean {


    /**
     * type : 1
     * list : [{"address":"djhasidfhjsdaf","symbol":"alsc"},{"address":"djhasidfhjsdaf2","symbol":"alsc,ETH"},{"address":"djhasidfhjsdaf3","symbol":"alsc"}]
     */

    private int type;
    private List<ListBean> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * address : djhasidfhjsdaf
         * symbol : alsc
         */

        private String address;
        private String symbol;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
