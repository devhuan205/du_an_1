package foply.ph52222.huanph52222.duan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import foply.ph52222.huanph52222.duan1.DAO.User_DAO;
import foply.ph52222.huanph52222.duan1.DbUser.DbUser;
import foply.ph52222.huanph52222.duan1.User.User;


public class MainActivity extends AppCompatActivity {
    DbUser dbUser;
    User_DAO userDao;
    User user;
    EditText edtUser, edtPass;
    Button btn_DangNhap, btn_Thoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ
        anhXa();

        clickLogin();
        clickThoat();
    }

    public void anhXa() {
        btn_DangNhap = findViewById(R.id.btn_DangNhap);
        btn_Thoat = findViewById(R.id.btn_Thoat);
        edtUser = findViewById(R.id.edt_User);
        edtPass = findViewById(R.id.edt_Password);

        userDao = new User_DAO(this);
        dbUser = new DbUser(this);
    }

    public void checkNull() {
        String txtUser = edtUser.getText().toString();
        String txtPass = edtPass.getText().toString();
        if (txtUser.isEmpty() || txtPass.isEmpty()) {
            if (txtUser.isEmpty()) {
                edtUser.setError("Chưa nhập tên đăng nhập");
            } else {
                edtPass.setError("Chưa nhập mật khẩu");
            }
        } else {
            user = (User) userDao.get_oneRow(txtUser);
            if (txtUser.equals(user.getUserName()) && txtPass.equals(user.getPassWord())) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(this, "Mật khẩu hoặc tài khoản không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clickLogin() {
        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNull();
            }
        });
    }

    public void clickThoat() {
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }
}