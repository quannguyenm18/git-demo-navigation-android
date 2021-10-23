package com.example.demonavigationdrawertoolbar.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demonavigationdrawertoolbar.R;
import com.example.demonavigationdrawertoolbar.database.DatabaseManager;
import com.example.demonavigationdrawertoolbar.model.KhoanThu;

public class UpdateKhoanThuActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_khoanthuUp,edt_moneyUp;
    Button btn_update;
   public KhoanThu khoanThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_khoan_thu);
        anhxa();
        getData();

    }

    private void getData() {
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            return;
        }
        khoanThu= (KhoanThu) bundle.get("OBJ");
        edt_moneyUp.setText(String.valueOf(khoanThu.getVNĐ()));
        edt_khoanthuUp.setText(khoanThu.getNameKT());

    }

    private void anhxa() {
        edt_khoanthuUp= findViewById(R.id.edt_khoanthuUpdate);
        edt_moneyUp=findViewById(R.id.edt_moneyUD);
        btn_update=findViewById(R.id.btn_updatenhapKT);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nameKT = edt_khoanthuUp.getText().toString().trim();
        String moneyKt = edt_moneyUp.getText().toString().trim();
        if (TextUtils.isEmpty(nameKT) || TextUtils.isEmpty(moneyKt)){
            return;
        }
        khoanThu.setNameKT(nameKT);
        khoanThu.setVNĐ(Integer.parseInt(moneyKt));
        DatabaseManager.getInstance(this).khoanThuDAO().UpdateKhoanThu(khoanThu);
        Toast.makeText(this, "update accesst", Toast.LENGTH_SHORT).show();
        Intent intentResult=new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();

    }
}