package com.example.app01;

import android.app.Application;

import java.util.List;

public class GlobalVariable extends Application {
    private String Companyuser,RUser,暫存fm1,暫存fm2,暫存ctime,門檻rClick,門檻kClick,門檻fClick,門檻hClick,門檻nClick,門檻wClick;
    private int 暫存rClick,暫存kClick,暫存fClick,暫存hClick,暫存nClick,暫存wClick,暫存tidindex;
    private String Start_year,Start_month,Start_ymd,Start_time;
    private int allday,allmonth,all,gg,alldayr,alldayf,alldayh,alldayn,alldayw,allmonthr,allmonthf,allmonthh,allmonthn,allmonthw;
    private String URL,RmainKey;
    private String endTime;
    private int His1Y,His1M,His1D,His1_R,His1_F,His1_H,His1_N,His1_W;
    private int rQuestY,rQuestM,rQuestD;
    private int rScore_R,rScore_F,rScore_H,rScore_N,rScore_W,rScore_KM;
    private String Com洽談,Com洽談r,Com洽談km,Com洽談f,Com洽談h,Com洽談n,Com洽談w,Com洽談Time;
    private int trafficLightsDetectorResult,speedChangeOrNot;
    private boolean overRed; //是否闖紅燈
    private int oilHeavy,oilLoss,overRedCount;//闖紅燈次數
    private String searchScore1,searchScore2;
    private double totalMileage,alldaykm,allmonthkm;//重油 急煞 里程數
    private String rQday;
    private int rQcount;
    private List<String> Quests;

    public List<String> getQuests() {
        return Quests;
    }
    public void setQuests(List<String> quests) {
        Quests = quests;
    }

    public String getCom洽談Time() {
        return Com洽談Time;
    }
    public void setCom洽談Time(String com洽談Time) {
        Com洽談Time = com洽談Time;
    }
    public String getCom洽談r() {
        return Com洽談r;
    }
    public void setCom洽談r(String com洽談r) {
        Com洽談r = com洽談r;
    }
    public String getCom洽談km() {
        return Com洽談km;
    }
    public void setCom洽談km(String com洽談km) {
        Com洽談km = com洽談km;
    }
    public String getCom洽談f() {
        return Com洽談f;
    }
    public void setCom洽談f(String com洽談f) {
        Com洽談f = com洽談f;
    }
    public String getCom洽談h() {
        return Com洽談h;
    }
    public void setCom洽談h(String com洽談h) {
        Com洽談h = com洽談h;
    }
    public String getCom洽談n() {
        return Com洽談n;
    }
    public void setCom洽談n(String com洽談n) {
        Com洽談n = com洽談n;
    }
    public String getCom洽談w() {
        return Com洽談w;
    }
    public void setCom洽談w(String com洽談w) {
        Com洽談w = com洽談w;
    }

    public boolean isOverRed() {
        return overRed;
    }
    public void setOverRed(boolean overRed) {
        this.overRed = overRed;
    }
    public int getrQcount() {
        return rQcount;
    }
    public void setrQcount(int rQcount) {
        this.rQcount = rQcount;
    }
    public int getoverRedCount() {
        return overRedCount;
    }
    public void setoverRedCount(int overRedCount) {
        this.overRedCount = overRedCount;
    }

    public String getrQday() {
        return rQday;
    }
    public void setrQday(String rQday) {
        this.rQday = rQday;
    }

    public int getoilHeavy() {
        return oilHeavy;
    }
    public void setoilHeavy(int oilHeavy) {
        this.oilHeavy = oilHeavy;
    }
    public int getoilLoss() {
        return oilLoss;
    }
    public void setoilLoss(int oilLoss) {
        this.oilLoss = oilLoss;
    }
    public double getTotalMileage() {
        return totalMileage;
    }
    public void setTotalMileage(double totalMileage) {
        this.totalMileage = totalMileage;
    }
    public String getSearchScore1() {
        return searchScore1;
    }
    public void setSearchScore1(String searchScore1) {
        this.searchScore1 = searchScore1;
    }
    public String getSearchScore2() {
        return searchScore2;
    }
    public void setSearchScore2(String searchScore2) {
        this.searchScore2 = searchScore2;
    }
    public int getSpeedChangeOrNot() {
        return speedChangeOrNot;
    }
    public void setSpeedChangeOrNot(int speedChangeOrNot) {
        this.speedChangeOrNot = speedChangeOrNot;
    }

    public int getTrafficLightsDetectorResult(){return trafficLightsDetectorResult;}
    public void setTrafficLightsDetectorResult(int trafficLightsDetectorResult){
        this.trafficLightsDetectorResult = trafficLightsDetectorResult;
    }

    public boolean getoverRed() {return overRed;}
    public void setoverRed(boolean overRed) {
        this.overRed = overRed;
    }

    public int getrQuestY() {
        return rQuestY;
    }
    public void setrQuestY(int rQuestY) {
        this.rQuestY = rQuestY;
    }
    public int getrQuestM() {
        return rQuestM;
    }
    public void setrQuestM(int rQuestM) {
        this.rQuestM = rQuestM;
    }
    public int getrQuestD() {
        return rQuestD;
    }
    public void setrQuestD(int rQuestD) {
        this.rQuestD = rQuestD;
    }

    public String getCom洽談() {
        return Com洽談;
    }
    public void setCom洽談(String com洽談) {
        Com洽談 = com洽談;
    }

    public int getrScore_R() {
        return rScore_R;
    }
    public void setrScore_R(int rScore_R) {
        this.rScore_R = rScore_R;
    }
    public int getrScore_F() {
        return rScore_F;
    }
    public void setrScore_F(int rScore_F) {
        this.rScore_F = rScore_F;
    }
    public int getrScore_H() {
        return rScore_H;
    }
    public void setrScore_H(int rScore_H) {
        this.rScore_H = rScore_H;
    }
    public int getrScore_N() {
        return rScore_N;
    }
    public void setrScore_N(int rScore_N) {
        this.rScore_N = rScore_N;
    }
    public int getrScore_W() {
        return rScore_W;
    }
    public void setrScore_W(int rScore_W) {
        this.rScore_W = rScore_W;
    }
    public int getrScore_KM() {
        return rScore_KM;
    }
    public void setrScore_KM(int rScore_KM) {
        this.rScore_KM = rScore_KM;
    }

    public int getHis1Y() {
        return His1Y;
    }
    public void setHis1Y(int his1Y) {
        His1Y = his1Y;
    }
    public int getHis1M() {
        return His1M;
    }
    public void setHis1M(int his1M) {
        His1M = his1M;
    }
    public int getHis1D() {
        return His1D;
    }
    public void setHis1D(int his1D) {
        His1D = his1D;
    }

    public String getRmainKey() {
        return RmainKey;
    }
    public void setRmainKey(String rmainKey) {
        RmainKey = rmainKey;
    }

    public int getHis1_R() {
        return His1_R;
    }
    public void setHis1_R(int his1_R) {
        His1_R = his1_R;
    }
    public int getHis1_F() {
        return His1_F;
    }
    public void setHis1_F(int his1_F) {
        His1_F = his1_F;
    }
    public int getHis1_H() {
        return His1_H;
    }
    public void setHis1_H(int his1_H) {
        His1_H = his1_H;
    }
    public int getHis1_N() {
        return His1_N;
    }
    public void setHis1_N(int his1_N) {
        His1_N = his1_N;
    }
    public int getHis1_W() {
        return His1_W;
    }
    public void setHis1_W(int his1_W) {
        His1_W = his1_W;
    }

    //修改 變數値
    public void setCompanyuser(String Companyuser){
        this.Companyuser = Companyuser;
    }
    public void set暫存fm1(String fm1){
        this.暫存fm1 = fm1;
    }
    public void set暫存fm2(String fm2){
        this.暫存fm2 = fm2;
    }
    public void set暫存ctime(String ctime){
        this.暫存ctime = ctime;
    }
    public void set暫存rClick(int rClick){
        this.暫存rClick = rClick;
    }
    public void set暫存kClick(int kClick){
        this.暫存kClick = kClick;
    }
    public void set暫存fClick(int fClick){
        this.暫存fClick = fClick;
    }
    public void set暫存hClick(int hClick){
        this.暫存hClick = hClick;
    }
    public void set暫存nClick(int nClick){
        this.暫存nClick = nClick;
    }
    public void set暫存wClick(int wClick){
        this.暫存wClick = wClick;
    }
    public void set暫存tidindex(int tidindex){
        this.暫存tidindex = tidindex;
    }
    public void set門檻rClick(String 門檻rClick){
        this.門檻rClick = 門檻rClick;
    }
    public void set門檻kClick(String 門檻kClick){
        this.門檻kClick = 門檻kClick;
    }
    public void set門檻fClick(String 門檻fClick){
        this.門檻fClick = 門檻fClick;
    }
    public void set門檻hClick(String 門檻hClick){
        this.門檻hClick = 門檻hClick;
    }
    public void set門檻nClick(String 門檻nClick){
        this.門檻nClick = 門檻nClick;
    }
    public void set門檻wClick(String 門檻wClick){
        this.門檻wClick = 門檻wClick;
    }


    //取得 變數值
    public String getCompanyuser() {
        return Companyuser;
    }
    public String get暫存fm1() {
        return 暫存fm1;
    }
    public String get暫存fm2() {
        return 暫存fm2;
    }
    public String get暫存ctime() {
        return 暫存ctime;
    }
    public int get暫存rClick() {
        return 暫存rClick;
    }
    public int get暫存kClick() {
        return 暫存kClick;
    }
    public int get暫存fClick() {
        return 暫存fClick;
    }
    public int get暫存hClick() {
        return 暫存hClick;
    }
    public int get暫存nClick() {
        return 暫存nClick;
    }
    public int get暫存wClick() {
        return 暫存wClick;
    }
    public int get暫存tidindex() {
        return 暫存tidindex;
    }
    public String get門檻rClick() {
        return 門檻rClick;
    }
    public String get門檻kClick() {
        return 門檻kClick;
    }
    public String get門檻fClick() {
        return 門檻fClick;
    }
    public String get門檻hClick() {
        return 門檻hClick;
    }
    public String get門檻nClick() {
        return 門檻nClick;
    }
    public String get門檻wClick() {
        return 門檻wClick;
    }

    public int getGg() {
        return gg;
    }

    public void setGg(int gg) {
        this.gg = gg;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRUser() {
        return RUser;
    }
    public void setRUser(String RUser) {
        this.RUser = RUser;
    }

    public String getStart_year() {
        return Start_year;
    }
    public void setStart_year(String start_year) {
        Start_year = start_year;
    }
    public String getStart_month() {
        return Start_month;
    }
    public void setStart_month(String start_month) {
        Start_month = start_month;
    }
    public String getStart_ymd() {
        return Start_ymd;
    }
    public void setStart_ymd(String start_ymd) {
        Start_ymd = start_ymd;
    }
    public String getStart_time() {
        return Start_time;
    }
    public void setStart_time(String start_time) {
        Start_time = start_time;
    }

    public int getAllday() {
        return allday;
    }
    public void setAllday(int allday) {
        this.allday = allday;
    }
    public int getAllmonth() {
        return allmonth;
    }
    public void setAllmonth(int allmonth) {
        this.allmonth = allmonth;
    }
    public int getAll() {
        return all;
    }
    public void setAll(int all) {
        this.all = all;
    }

    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getAlldayr() {
        return alldayr;
    }
    public void setAlldayr(int alldayr) {
        this.alldayr = alldayr;
    }
    public double getAlldaykm() {
        return alldaykm;
    }
    public void setAlldaykm(double alldaykm) {
        this.alldaykm = alldaykm;
    }
    public int getAlldayf() {
        return alldayf;
    }
    public void setAlldayf(int alldayf) {
        this.alldayf = alldayf;
    }
    public int getAlldayh() {
        return alldayh;
    }
    public void setAlldayh(int alldayh) {
        this.alldayh = alldayh;
    }
    public int getAlldayn() {
        return alldayn;
    }
    public void setAlldayn(int alldayn) {
        this.alldayn = alldayn;
    }
    public int getAlldayw() {
        return alldayw;
    }
    public void setAlldayw(int alldayw) {
        this.alldayw = alldayw;
    }
    public int getAllmonthr() {
        return allmonthr;
    }
    public void setAllmonthr(int allmonthr) {
        this.allmonthr = allmonthr;
    }
    public double getAllmonthkm() {
        return allmonthkm;
    }
    public void setAllmonthkm(double allmonthkm) {
        this.allmonthkm = allmonthkm;
    }
    public int getAllmonthf() {
        return allmonthf;
    }
    public void setAllmonthf(int allmonthf) {
        this.allmonthf = allmonthf;
    }
    public int getAllmonthh() {
        return allmonthh;
    }
    public void setAllmonthh(int allmonthh) {
        this.allmonthh = allmonthh;
    }
    public int getAllmonthn() {
        return allmonthn;
    }
    public void setAllmonthn(int allmonthn) {
        this.allmonthn = allmonthn;
    }
    public int getAllmonthw() {
        return allmonthw;
    }
    public void setAllmonthw(int allmonthw) {
        this.allmonthw = allmonthw;
    }
}
