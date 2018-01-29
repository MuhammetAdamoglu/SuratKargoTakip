package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    public getData getData;
    private static ViewPager viewPager;

    @Override
    public void onBackPressed() {

        if(slidingUpPanelLayout.getPanelState()!=SlidingUpPanelLayout.PanelState.COLLAPSED){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }else {
            super.onBackPressed();
        }
    }

    public static void setCurrentItem (int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }
    static String CargoNumber="";

    Buyer buyer;
    Sender sender;
    Product product;

    DataBase dataBase;
    Cargos cargos;

    ConstraintLayout EmptyScreen;
    EditText Et_cargoNumber;
    ImageView ımageView_search;
    View ConnectLine;
    SlidingUpPanelLayout slidingUpPanelLayout;
    TextView cargoLoaction,cargoName;
    static ListView listView;

    static MainActivity here;
    ProgressDialog dialog;

    SQLite myDb;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        here=this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getColor(R.color.colorPrimary));
        }

        myDb= new SQLite(getApplicationContext());


        buyer=new Buyer(this);
        sender=new Sender(this);
        product=new Product(this);

        listView=findViewById(R.id.ListView);
        ConnectLine=findViewById(R.id.ConnectLine);

        cargos=new Cargos();
        cargos.start(getApplicationContext());

        /*if(CargoNumber==null)
            CargoNumber= cargos.getCargos().get(0);*/


        cargoName=findViewById(R.id.cargoName);
        cargoName.setText(myDb.getCargoName(CargoNumber));

        dataBase =new DataBase(this);

        getData=new getData();
        //getData.start(this,CargoNumber);

        cargoLoaction=findViewById(R.id.cargoLocation);

        ConnectFalse();

        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager =findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout =findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        slidingUpPanelLayout=findViewById(R.id.sliding_layout);
        Et_cargoNumber=findViewById(R.id.Et_cargoNumber);
        ımageView_search=findViewById(R.id.imageView_search);

        EmptyScreen=findViewById(R.id.empty);

        dialog = new ProgressDialog(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        }, 100);



        slidingUpPanelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseKeyBoard();
            }
        });
        slidingUpPanelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelCollapsed(View panel) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onPanelExpanded(View panel) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });

        slidingUpPanelLayout.setTouchEnabled(false);

        Et_cargoNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(!Et_cargoNumber.getText().toString().equals("")){

                        dialog.setMessage("Kontrol Ediliyor...");
                        dialog.setCancelable(false);
                        dialog.show();

                        CloseKeyBoard();
                        new QueryCargoNumber(Et_cargoNumber.getText().toString(),2);

                    }
                }
                return false;
            }
        });

        ımageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Kontrol Ediliyor...");
                dialog.setCancelable(false);
                dialog.show();

                CloseKeyBoard();
                new QueryCargoNumber(Et_cargoNumber.getText().toString(),2);
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.setMessage("Açılıyor...");
                dialog.setCancelable(false);
                dialog.show();

                CloseKeyBoard();
                new QueryCargoNumber( cargos.getCargos().get(i),2);

            }
        });

        new AlertDialog(this,listView);

        setupTabIcons();
    }

    private void CloseKeyBoard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    void TrueQuery(String cargoNumber){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        new OpenCargo(cargoNumber,this,getData);
    }

    void FalseQuery(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(this, "Sorgu Bulunamadı", Toast.LENGTH_SHORT).show();
    }


    private String additional(CharSequence text,int id){
        char [] thick_vowel={'a','ı','o','u'};
        char [] thin_vowel={'e','i','ö','ü'};

        String additional="";
        
        for (int i=0; i<text.length() ;i++){
            for(int j=0; j<thick_vowel.length;j++)
                if(thick_vowel[j]==text.charAt(i)){
                    if(id==0)
                        additional="da";
                    else if(id==1)
                        additional="daydı";
                }
                else if(thin_vowel[j]==text.charAt(i)){
                    if(id==0)
                        additional="de";
                    else if(id==1)
                        additional="deydi";
                }

        }

        return additional;
    }

    @SuppressLint("SetTextI18n")
    public void ConnectFalse(){
        dataBase.getSaveData(MainActivity.CargoNumber);
        if(dataBase.ControlData(MainActivity.CargoNumber))
            cargoLoaction.setText("Kargonuz "+ dataBase.getProduc("Son Bulunduğu Yer")+additional(dataBase.getProduc("Son Bulunduğu Yer"),1));

    }

    @SuppressLint("SetTextI18n")
    public void ConnectTrue(){

        cargos.start(getApplicationContext());
        Et_cargoNumber.setText("");
        cargoName.setText(myDb.getCargoName(CargoNumber));

        dataBase.getSaveData(MainActivity.CargoNumber);

        cargoLoaction.setText("Kargonuz Şuan "+ dataBase.getProduc("Son Bulunduğu Yer")+additional(dataBase.getProduc("Son Bulunduğu Yer"),0));

        product.Load();
        sender.Load();
        buyer.Load();

        ConnectLine.setBackgroundColor(Color.parseColor("#76FF03"));

    }

    void CargoOpened(){
        EmptyScreen.setVisibility(View.GONE);
        slidingUpPanelLayout.setTouchEnabled(true);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }
    void CargoClosed(){
        EmptyScreen.setVisibility(View.VISIBLE);
        slidingUpPanelLayout.setTouchEnabled(false);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    private void setupTabIcons() {
        //tabLayout.getTabAt(2).setIcon(R.drawable.icon_settings);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(product, "Ürün");
        adapter.addFragment(sender, "Gönderen");
        adapter.addFragment(buyer, "Alıcı");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
