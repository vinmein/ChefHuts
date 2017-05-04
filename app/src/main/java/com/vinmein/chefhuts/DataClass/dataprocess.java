package com.vinmein.chefhuts.DataClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dinesh on 14/1/17.
 */

public class dataprocess {
    private static dataprocess instance = null;
    SharedPreferences prefs;
    private ArrayList<Float> graphvalue = new ArrayList<Float>(); ;
    private String restaurantId;
    private JSONArray Menu;
    private String diningId;
    private String host = "http://foodiehuts.com";
    private String menuStr;
    private JSONArray menuData;
    private String fBMToken = "";
    private String Rqstid="";
    private String rspsehtlid="";
    private String reqstJson="";
    private boolean qrStatus = false;
    private boolean reqalert=false;
    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private dataprocess(Context context) {
        prefs = context.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
    }


    public static dataprocess getInstance(Context context) {
        if(instance == null) {
            instance = new dataprocess(context);
        }
        return instance;
    }

    /* Other methods protected by singleton-ness */
    public String getRestaurantId() {
        return prefs.getString("restaurantID",null);
    }

    public String getDiningId() {
        return prefs.getString("diningID",null);
    }

    public String getDiningSession(){
        return prefs.getString("diningSession",null);
    }

    public String getfBMToken(){
        return fBMToken;
    }

    public void setfBMToken(String token){
        this.fBMToken = token;
    }

    public String getUserId() {
        return prefs.getString("id",null);
    }

    public String getfirstName() {
        return prefs.getString("fName",null);
    }

    public String getlastName() {
        return prefs.getString("lName",null);
    }

    public String getUserName() {
        return prefs.getString("uName",null);
    }

    public String getEmail() {
        return prefs.getString("Useremail",null);
    }

    public String gethostid(){return prefs.getString("hostuser",null);}

    public void setDiningSession(String diningSession){
        prefs.edit().putString("diningSession",diningSession).apply();
    }

    public void setMenuSession( String menuSession){
        prefs.edit().putString("MenuSession",menuSession).apply();
    }

    public void setPreviousMenuSession( String PreviousSession){
        prefs.edit().putString("PrevMenuSession",PreviousSession).apply();
    }

    public void setRestaurantId(String restaurantId) {
        prefs.edit().putString("restaurantID",restaurantId).apply();
    }

    public void setDiningId(String diningId) {
        prefs.edit().putString("diningID",diningId).apply();
    }

    public void setTour(Boolean tour) {
        prefs.edit().putBoolean("tour",tour).apply();
    }

    public void setHostuser(String HostUserId) {
        prefs.edit().putString("hostuser",HostUserId).apply();
    }

    public void setQRstatus(Boolean status){
        this.qrStatus = status;
    }
    public void setReqalert(Boolean status){
        this.reqalert = status;
    }

    public void toggleQRstatus(){
        this.qrStatus = !this.qrStatus;
    }

    public Boolean getQRstatus(){
        return this.qrStatus;
    }

    public Boolean tour(){
        return prefs.getBoolean("tour", false);
    }

    public Boolean getReqalert(){
        return this.reqalert;
    }
    public String gethost(){
        return this.host;
    }

    public void clearLogout(){
        prefs.edit().clear().apply();
    }

    public String getMenuSession(){
        return prefs.getString("MenuSession",null);
    }
    public String getPreviousMenuSession(){
        return prefs.getString("PrevMenuSession",null);
    }
    public void setRspnsRqstid(String Rqstid ){
        this.Rqstid = Rqstid;
    }

    public String getRspnsRqid(){
        return this.Rqstid;
    }

    public void setRspnshtlid(String rspsehtlid ){
        this.rspsehtlid= rspsehtlid;
    }

    public String getRspnshtlid(){
        return this.rspsehtlid;
    }

    public String getReqstJson(){
        return this.reqstJson;
    }
    public void setReqstJson(String reqsJson){
        this.reqstJson=reqsJson;
    }
    public void setQRData(String qrData) {
        Log.i("Qrresultdata",qrData);

        try {
            JSONObject QRObj = new JSONObject(qrData);
            this.restaurantId = String.valueOf(QRObj.get("restaurantID"));
            prefs.edit().putString("restaurantID",this.restaurantId).apply();

            this.diningId = String.valueOf(QRObj.get("tableID"));
            prefs.edit().putString("diningID",this.diningId).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("process",qrData);
    }

    public void setUserData(String userData) {

        try{
            JSONObject userObj=new JSONObject(userData);
            prefs.edit().putString("id", String.valueOf(userObj.get("_id"))).apply();
            prefs.edit().putString("uName", String.valueOf(userObj.get("name"))).apply();
            prefs.edit().putString("Useremail", String.valueOf(userObj.get("email"))).apply();
            prefs.edit().putString("fName", String.valueOf(userObj.get("fname"))).apply();
            prefs.edit().putString("lName", String.valueOf(userObj.get("lname"))).apply();
            prefs.edit().putString("token", String.valueOf(userObj.get("token"))).apply();

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getMenuData(){
        return this.menuStr;
    }

    public void setMenu(String responseStr) {

        this.menuStr = responseStr;
        try {
            this.Menu = new JSONArray(responseStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setMenuData(String menuData) {
        try {
            this.menuData = new JSONArray(menuData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Object getMenuData(int tabPosition, int position) throws JSONException {
        JSONObject js = (JSONObject) this.menuData.get(tabPosition);
        JSONArray itemArray = (JSONArray) js.get("item");
        return itemArray.get(position);
    }

    public JSONArray getMenu(){
        return this.Menu;
    }

    public ArrayList<Float> getGraphvalue(){
        return this.graphvalue;
    }

    public void SetGraphvalue(float g1 )
    {
        graphvalue.add(g1);
    }
}
