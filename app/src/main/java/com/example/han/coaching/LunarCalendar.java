package com.example.han.coaching;

import com.ibm.icu.util.ChineseCalendar;

import java.util.Calendar;

/**
 * Created by han on 2015-11-24.
 */
public class LunarCalendar {

    private Calendar cal;
    private ChineseCalendar cc;

    public LunarCalendar() {
        //default TimeZone, Locale 을 사용
        cal = Calendar.getInstance();
        cc = new ChineseCalendar();
    }

    //테스트를 위한 메인 메소드
    public static void main(String[] args) {

        LunarCalendar lc = new LunarCalendar();

    }


    /**
     * 양력(yyyyMMdd) -> 음력(yyyyMMdd)
     * : 양력을 음력으로 변환 처리한다.
     */
    public synchronized String toLunar(String yyyymmdd) {

        if( yyyymmdd == null ) return "";

        String date = yyyymmdd.trim();

        //
        if( date.length() != 8) {
            if( date.length() == 4) {
                date = date + "0101";
            }else if(date.length() == 6){
                date = date + "01";
            }else if(date.length() > 8) {
                date = date.substring(0,8);
            }else {
                return "";
            }
        }

        cal.set( Calendar.YEAR,  Integer.parseInt(date.substring(0,4)));
        cal.set( Calendar.MONTH,  Integer.parseInt(date.substring(4,6))-1);
        cal.set( Calendar.DAY_OF_MONTH,  Integer.parseInt(date.substring(6)));

        cc.setTimeInMillis(cal.getTimeInMillis());


        //ChinessCalendar.EXTENDED_YEAR 는 Calendar.YEAR 값과 2637 만큼의 차이를 가짐.

        int y = cc.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
        int m = cc.get(ChineseCalendar.MONTH)+1;
        int d = cc.get(ChineseCalendar.DAY_OF_MONTH);

        //출력할 문자열을 저장하기 위한 스트링 버퍼 객체
        StringBuffer sb = new StringBuffer();

        if( y < 1000 ) sb.append("0");
        else if(y<100) sb.append("00");
        else if(y<10) sb.append("000");
        sb.append(y); //년

        if(m < 10 ) sb.append("0");
        sb.append(m); //월

        if(d < 10) sb.append("0");
        sb.append(d); //일

        return sb.toString();
    }

    /**
     * 음력(yyyyMMdd) -> 양력(yyyyMMdd)
     * : 음력을 양력으로 변환 처리한다.
     */
    public synchronized String fromLunar(String yyyymmdd) {

        if( yyyymmdd == null ) return "";

        String date = yyyymmdd.trim();
        if( date.length() != 8) {
            if( date.length() == 4) {
                date = date + "0101";
            }else if(date.length() == 6){
                date = date + "01";
            }else if(date.length() > 8) {
                date = date.substring(0,8);
            }else {
                return "";
            }
        }

        //ChinessCalendar.EXTENDED_YEAR 는 Calendar.YEAR 값과 2637 만큼의 차이를 가짐.
        cc.set( ChineseCalendar.EXTENDED_YEAR,  Integer.parseInt(date.substring(0,4))+2637);
        cc.set( ChineseCalendar.MONTH,  Integer.parseInt(date.substring(4,6))-1);
        cc.set( ChineseCalendar.DAY_OF_MONTH,  Integer.parseInt(date.substring(6)));

        cal.setTimeInMillis(cc.getTimeInMillis());

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH)+1;
        int d = cal.get(Calendar.DAY_OF_MONTH);

        StringBuffer sb = new StringBuffer();
        if( y < 1000 ) sb.append("0");
        else if(y<100) sb.append("00");
        else if(y<10) sb.append("000");
        sb.append(y);

        if(m < 10 ) sb.append("0");
        sb.append(m);

        if(d < 10) sb.append("0");
        sb.append(d);

        return sb.toString();
    }
}