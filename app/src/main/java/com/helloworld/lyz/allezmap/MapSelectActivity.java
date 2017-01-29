package com.helloworld.lyz.allezmap;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.helloworld.lyz.allezmap.R.id.map_my_location_rad_btn;
import static com.helloworld.lyz.allezmap.R.id.map_other_location_rad_btn;

public class MapSelectActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    protected static final String TAG = "MainActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    private EditText editTextTime;
    private EditText editTextDate;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
    //    =====================
//    listview
    private MapSlideCutListView mapSlideCutListView;
    private ArrayAdapter<String> mAdapter;
    //数据
    private List<String> mDatas =new ArrayList<String>();
    //======================
//单选按钮
    //对控件对象进行声明
    private RadioGroup radioGroup;
    private RadioButton myLocationRadioButton;
    private RadioButton otherLocationRadioButton;
    //imageview 添加
    private ImageView map_imageview_add_mylocation;
    private ImageView map_imageview_add_otherlocation;


    /**
     * Request code for the autocomplete activity. This will be used to identify results from the
     * autocomplete activity in onActivityResult.
     */
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select);

        init();
        buildGoogleApiClient();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.map_select_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.map_select_title);
        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，
        actionBar.setDisplayShowHomeEnabled(true);

        //groupbutton
        radioGroup = (RadioGroup) findViewById(R.id.map_select_location_group);
        myLocationRadioButton = (RadioButton) findViewById(map_my_location_rad_btn);
        otherLocationRadioButton = (RadioButton) findViewById(map_other_location_rad_btn);

        //image view
        map_imageview_add_mylocation = (ImageView) findViewById(R.id.map_imageview_add_mylocation);
        map_imageview_add_otherlocation = (ImageView) findViewById(R.id.map_imageview_add_otherlocation);
        //时间
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        editTextTime = (EditText) findViewById(R.id.map_ed_select_time);
        editTextDate = (EditText) findViewById(R.id.map_ed_select_date);
        //设置为不可编辑状态
        editTextDate.setKeyListener(null);
        editTextTime.setKeyListener(null);

        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        editTextTime.setText(hourOfDay + ":" + minute);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实例化一个DatePickerDialog的对象
                 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，当用户选择好日期点击done会调用里面的onDateSet方法
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(MapSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, monthOfYear, dayOfMonth);

                datePickerDialog.show();
            }
        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实例化一个TimePickerDialog的对象
                 * 第二个参数是一个TimePickerDialog.OnTimeSetListener匿名内部类，当用户选择好时间后点击done会调用里面的onTimeset方法
                 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(MapSelectActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTextTime.setText("Time: " + hourOfDay + ":" + minute);
                    }
                }, hourOfDay, minute, true);

                timePickerDialog.show();
            }
        });
//====================listview


//        //listview 数据
//        mDatas = new ArrayList<String>();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                //当前位置
                if (checkedId == map_my_location_rad_btn) {
                   final String mapData= obtenirLocation();

                    map_imageview_add_otherlocation.setClickable(false);
                    //添加当前位置
                    map_imageview_add_mylocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(MapSelectActivity.this, "map_imageview_add_mylocation ", Toast.LENGTH_LONG).show();
                            if (mDatas.size()<=3){
                                mDatas.add(mapData.toString().trim());
                                maplistview();
                            }
                            else{
                                Toast.makeText(MapSelectActivity.this, "最多支持四个地址!", Toast.LENGTH_LONG).show();


                            }
                        }
                    });


//                    Toast.makeText(MapSelectActivity.this,   "map_my_location_rad_btn " , Toast.LENGTH_LONG).show();
                } else if (checkedId == map_other_location_rad_btn) {
                    Toast.makeText(MapSelectActivity.this, "map_other_location_rad_btn ", Toast.LENGTH_LONG).show();
                    map_imageview_add_mylocation.setClickable(false);

                    map_imageview_add_otherlocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MapSelectActivity.this, "map_imageview_add_otherlocation ", Toast.LENGTH_LONG).show();

                            if (mDatas.size()<=3){
                                openAutocompleteActivity();
//                                mDatas.add("数据2");
//                                maplistview();
                            }else{
                                Toast.makeText(MapSelectActivity.this, "最多支持四个地址", Toast.LENGTH_LONG).show();


                            }


                        }
                    });
                }
            }
        });




    }

    //打开搜索地址的页面
    private void openAutocompleteActivity() {

        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

//            Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
//                返回的结果
//                Log.i(TAG, "Place Selected: " + place.getName());
                Toast.makeText(this, place.getName()+place.getAddress().toString()+"！！！！", Toast.LENGTH_SHORT).show();

                //填充数据  listview
                mDatas.add(place.getName().toString().trim()+place.getAddress().toString().trim());
                maplistview();


//                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
//                        place.getId(), place.getAddress(), place.getPhoneNumber(),
//                        place.getWebsiteUri(),place.getLatLng()));

                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                } else {
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void maplistview() {
        //        ======================================
        mapSlideCutListView = (MapSlideCutListView) findViewById(R.id.slideCutListView);
        // 不要直接Arrays.asList
//        mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "HelloWorld"));
//        mDatas.add("0000");

            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);

            mapSlideCutListView.setAdapter(mAdapter);




        mapSlideCutListView.setDelButtonClickListener(new MapSlideCutListView.DelButtonClickListener() {
            @Override
            public void clickHappend(final int position) {
                Toast.makeText(MapSelectActivity.this, position + " : " + mAdapter.getItem(position), Toast.LENGTH_LONG).show();
                mAdapter.remove(mAdapter.getItem(position));
            }
        });

        mapSlideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MapSelectActivity.this, position + " : " + mAdapter.getItem(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    //返回上一级菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        obtenirLocation();
    }

    //获取当前位置坐标的方法
    public String obtenirLocation() {
// Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
//            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
//                    mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
//                    mLastLocation.getLongitude()));

            String str = mLastLocation.getLongitude() + "" + mLastLocation.getLatitude()+ mLastLocation.getProvider();

            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            return str;

        } else {
//            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
            return null;
        }


    }

    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
//        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

//
}
