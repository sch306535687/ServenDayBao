package sun.ch.servendaybao.domain;

/**
 * Created by sunch on 2017/3/31.
 */

public class User {
    public int RCD;
    public UserData Data;

    public class UserData {
        public String Token;//token值
        public InfoData UserInfo;

        public class InfoData {
            private String UserId;
            private String NickName;
            private String Email;
            private String Phone;
            private String Name;
            private String IDType;
            private String IDNo;
            private String SexType;
            private String SalesManPhone;
            private String ShopName;
            private String ShopAddress;
            private String ShopCountyCode;
            private String ShopCounty;
            private String DepositBank;
            private String AccountNumber;
            private String AccountName;//用户名字
            private String IsPersonal;
            private String BankName;//银行名称
            private String Status;
            private String Sys_Type;
            private String DayBao_Url;//七天宝链接

            public InfoData(String accountName, String dayBao_Url, String bankName) {
                AccountName = accountName;
                DayBao_Url = dayBao_Url;
                BankName = bankName;
            }

            public String getDayBao_Url() {
                return DayBao_Url;
            }

            public String getBankName() {
                return BankName;
            }

            public String getAccountName() {
                return AccountName;
            }

            public void setAccountName(String accountName) {
                AccountName = accountName;
            }

            public void setDayBao_Url(String dayBao_Url) {
                DayBao_Url = dayBao_Url;
            }

            public void setBankName(String bankName) {
                BankName = bankName;
            }

            @Override
            public String toString() {
                return "InfoData{" +
                        "DayBao_Url='" + DayBao_Url + '\'' +
                        ", BankName='" + BankName + '\'' +
                        ", AccountName='" + AccountName + '\'' +
                        '}';
            }
        }

        public UserData(String token, InfoData userInfo) {
            Token = token;
            UserInfo = userInfo;
        }

        public String getToken() {
            return Token;
        }

        public InfoData getUserInfo() {
            return UserInfo;
        }

        public void setToken(String token) {
            Token = token;
        }

        public void setUserInfo(InfoData userInfo) {
            UserInfo = userInfo;
        }

        @Override
        public String toString() {
            return "UserData{" +
                    "Token='" + Token + '\'' +
                    ", UserInfo=" + UserInfo +
                    '}';
        }
    }

}
