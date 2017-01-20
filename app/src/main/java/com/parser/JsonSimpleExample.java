package com.parser;

import android.content.Context;

import com.mrprona.dota2assitant.hero.api.TalentTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Admin on 12/19/2016.
 */

public class JsonSimpleExample {


    public static File getRobotCacheFile(Context context) throws IOException {
        File cacheFile = new File(context.getCacheDir(), "talent_english.json");
        try {
            InputStream inputStream = context.getAssets().open("talent_english.json");
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("Could not open robot png", e);
        }
        return cacheFile;
    }

    public static List<TalentTree> ConvertJsonFile(Context context){
        ArrayList<TalentTree> arrTalentTree= new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(getRobotCacheFile(context)));

            JSONArray jsonArrayHeros = (JSONArray) obj;

            Iterator<JSONObject> iterator = jsonArrayHeros.iterator();

            while (iterator.hasNext()) {
                TalentTree mTempTalentTree= new TalentTree();
                JSONObject tempObject= iterator.next();

                String id = (String)tempObject.get("id");
                mTempTalentTree.setId(Integer.parseInt(id));

                String name = (String)tempObject.get("name");
                mTempTalentTree.setName(name);

                JSONArray arrayTalents= (JSONArray) tempObject.get("talents");
                JSONObject jsonTalentObj= (JSONObject) arrayTalents.get(0);


                //25LEFT
                String col25Left= (String)jsonTalentObj.get("t4left");
                mTempTalentTree.setLv25Left(col25Left);
                //20LEFT
                String col20Left= (String) jsonTalentObj.get("t3left");
                mTempTalentTree.setLv20Left(col20Left);

                //15LEFT
                String col15Left= (String) jsonTalentObj.get("t2left");
                mTempTalentTree.setLv15Left(col15Left);

                //10LEFT
                String col10Left= (String) jsonTalentObj.get("t1left");;
                mTempTalentTree.setLv10Left(col10Left);


                //25RIGHT
                String col25Right= (String)jsonTalentObj.get("t4right");
                mTempTalentTree.setLv25Right(col25Right);

                //20RIGHT
                String col20Right= (String)jsonTalentObj.get("t3right");
                mTempTalentTree.setLv20Right(col20Right);

                //15RIGHT
                String col15Right= (String)jsonTalentObj.get("t2right");
                mTempTalentTree.setLv15Right(col15Right);

                //10RIGHT
                String col10Right= (String)jsonTalentObj.get("t1right");
                mTempTalentTree.setLv10Right(col10Right);

                arrTalentTree.add(mTempTalentTree);

             /*   System.out.println(iterator.next());
                String name = (String) jsonObject.get("name");
                System.out.println(name);

                long age = (Long) jsonObject.get("age");
                System.out.println(age);

                // loop array
                JSONArray msg = (JSONArray) jsonObject.get("messages");*/
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return arrTalentTree;
    }

}
