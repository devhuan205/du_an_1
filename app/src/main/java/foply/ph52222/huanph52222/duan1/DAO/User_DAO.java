package foply.ph52222.huanph52222.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import foply.ph52222.huanph52222.duan1.DbUser.DbUser;
import foply.ph52222.huanph52222.duan1.User.User;


public class User_DAO {
    private DbUser dbUser;
    private SQLiteDatabase sqLiteDatabase;
    public User_DAO(Context context) {
        dbUser = new DbUser(context);
        sqLiteDatabase = dbUser.getWritableDatabase();
    }

    public boolean Insert(User user){
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("PassWord", user.getPassWord());

        long row = (long) sqLiteDatabase.insert("User", null, values);
        return row > 0;
    }

    public User get_oneRow(String dieukien){
        User user = null;

        String[] dieu_kien = {String.valueOf(dieukien)};
        sqLiteDatabase = dbUser.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT UserName, PassWord FROM User WHERE UserName = ?", dieu_kien);
        if (cursor != null && cursor.getCount() == 1){
            cursor.moveToFirst();
            String User = cursor.getString(0);
            String Pass = cursor.getString(1);
            user = new User(User, Pass);
        }
        return user;
    }
}
