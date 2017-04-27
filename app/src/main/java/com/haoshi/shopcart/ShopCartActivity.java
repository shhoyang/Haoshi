package com.haoshi.shopcart;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.shopcart.adapter.CategoryAdapter;
import com.haoshi.shopcart.adapter.GoodsAdapter;
import com.haoshi.shopcart.adapter.ProductAdapter;
import com.haoshi.shopcart.bean.CategoryBean;
import com.haoshi.shopcart.bean.GoodsBean;
import com.haoshi.shopcart.view.HaoListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    //控件
    private ListView lvCategory, lvGood;
    private TextView textCart, textCount, textMoney;
    private LinearLayout linearCart;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;

    private CategoryAdapter categoryAdapter;
    private GoodsAdapter goodsAdapter;
    private ProductAdapter productAdapter;

    private List<GoodsBean> list1 = new ArrayList<>();
    private List<GoodsBean> list2 = new ArrayList<>();
    private List<GoodsBean> list3 = new ArrayList<>();
    private List<CategoryBean> catograyBeanList = new ArrayList<>();
    private List<GoodsBean> goodsBeanList = new ArrayList<>();
    private SparseArray<GoodsBean> selectedList;

    private Double totalMoney = 0.00;
    private DecimalFormat decimalFormat;
    private ViewGroup anim_mask_layout;//动画层

    public ShopCartActivity() {
    }

    @Override
    public void initView() {
        setEnableSwipe(false);
        lvCategory = (ListView) findViewById(R.id.lv_category);
        lvGood = (ListView) findViewById(R.id.lv_good);
        textCart = (TextView) findViewById(R.id.text_cart);
        textCount = (TextView) findViewById(R.id.text_count);
        textMoney = (TextView) findViewById(R.id.text_money);
        linearCart = (LinearLayout) findViewById(R.id.linear_cart);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottom_sheet);

        linearCart.setOnClickListener(this);
        lvCategory.setOnItemClickListener(this);
    }

    @Override
    public void setData() {
        decimalFormat = new DecimalFormat();
        selectedList = new SparseArray<>();
        GoodsBean goodsBean;
        for (int i = 10; i < 20; i++) {
            goodsBean = new GoodsBean();
            goodsBean.setTitle("iPhone7" + i);
            goodsBean.setProductId(i);
            goodsBean.setCategoryId(i);
            goodsBean.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492888118908&di=523ad63ca427c687902ea04e67b45522&imgtype=0&src=http%3A%2F%2Fimg.xiazaizhijia.com%2Fuploads%2F2016%2F0617%2F20160617112834356.jpg");
            goodsBean.setOriginalPrice("6000");
            goodsBean.setPrice("5000");
            list1.add(goodsBean);
        }

        for (int i = 20; i < 30; i++) {
            goodsBean = new GoodsBean();
            goodsBean.setTitle("Galaxy S7 edge" + i);
            goodsBean.setProductId(i);
            goodsBean.setCategoryId(i);
            goodsBean.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492888015539&di=5c5247d32bc5f201f553e713486b43d0&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fproduct%2F6046%2F604666%2Fq.jpg");
            goodsBean.setOriginalPrice("5000");
            goodsBean.setPrice("4000");
            list2.add(goodsBean);
        }

        for (int i = 30; i < 40; i++) {
            goodsBean = new GoodsBean();
            goodsBean.setTitle("Huawei mate9" + i);
            goodsBean.setProductId(i);
            goodsBean.setCategoryId(i);
            goodsBean.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492887960647&di=7eaae4cd14d8ea4f176ae055eb93e6b2&imgtype=0&src=http%3A%2F%2Fimg.xiazaizhijia.com%2Fuploads%2F2016%2F0930%2F20160930090140136.jpg");
            goodsBean.setOriginalPrice("4000");
            goodsBean.setPrice("3000");
            list3.add(goodsBean);
        }


        CategoryBean categoryBean1 = new CategoryBean();
        categoryBean1.setCount(3);
        categoryBean1.setKind("苹果旗舰店");
        categoryBean1.setList(list1);
        catograyBeanList.add(categoryBean1);
        CategoryBean categoryBean2 = new CategoryBean();
        categoryBean2.setCount(4);
        categoryBean2.setKind("三星旗舰店");
        categoryBean2.setList(list2);
        catograyBeanList.add(categoryBean2);
        CategoryBean categoryBean3 = new CategoryBean();
        categoryBean3.setCount(5);
        categoryBean3.setKind("华为旗舰店");
        categoryBean3.setList(list3);
        catograyBeanList.add(categoryBean3);

        //默认值
        goodsBeanList.clear();
        goodsBeanList.addAll(catograyBeanList.get(0).getList());
        //分类
        categoryAdapter = new CategoryAdapter(catograyBeanList);
        lvCategory.setAdapter(categoryAdapter);
        //商品
        goodsAdapter = new GoodsAdapter(this, goodsBeanList, categoryAdapter);
        lvGood.setAdapter(goodsAdapter);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_shop_cart;
    }

    @Override
    public String setTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_clear:
                clearCart();
                break;
            case R.id.linear_cart:
                showBottomSheet();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        goodsBeanList.clear();
        goodsBeanList.addAll(catograyBeanList.get(i).getList());
        categoryAdapter.setSelection(i);
        categoryAdapter.notifyDataSetChanged();
        goodsAdapter.notifyDataSetChanged();
    }

    /**
     * 创建购物车view
     */
    private void showBottomSheet() {
        bottomSheet = createBottomSheetView();
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    /**
     * 查看购物车布局
     *
     * @return
     */
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.shop_cart_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        HaoListView lv_product = (HaoListView) view.findViewById(R.id.lv_product);
        TextView textClear = (TextView) view.findViewById(R.id.text_clear);
        textClear.setOnClickListener(this);
        productAdapter = new ProductAdapter(this, goodsAdapter, selectedList);
        lv_product.setAdapter(productAdapter);
        return view;
    }

    /**
     * 清空购物车
     */
    public void clearCart() {
        selectedList.clear();
        goodsBeanList.clear();
        int size = catograyBeanList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                catograyBeanList.get(i).setCount(0);
                for (int j = 0; j < catograyBeanList.get(i).getList().size(); j++) {
                    catograyBeanList.get(i).getList().get(j).setNum(0);
                }
            }
            goodsBeanList.addAll(catograyBeanList.get(0).getList());
            categoryAdapter.setSelection(0);
            //刷新不能删
            categoryAdapter.notifyDataSetChanged();
            goodsAdapter.notifyDataSetChanged();
        }
        update();
    }

    /**
     * 刷新布局 总价、购买数量等
     */
    private void update() {
        int size = selectedList.size();
        int count = 0;
        for (int i = 0; i < size; i++) {
            GoodsBean item = selectedList.valueAt(i);
            count += item.getNum();
            totalMoney += item.getNum() * Double.parseDouble(item.getPrice());
        }
        textMoney.setText("￥" + String.valueOf(decimalFormat.format(totalMoney)));
        totalMoney = 0.00;
        if (count < 1) {
            textCount.setVisibility(View.GONE);
        } else {
            textCount.setVisibility(View.VISIBLE);
        }

        textCount.setText(String.valueOf(count));

        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }

        if (goodsAdapter != null) {
            goodsAdapter.notifyDataSetChanged();
        }

        if (categoryAdapter != null) {
            categoryAdapter.notifyDataSetChanged();
        }

        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    /**
     * 根据商品id获取当前商品的采购数量
     *
     * @param id
     * @return
     */
    public int getSelectedItemCountById(int id) {
        GoodsBean temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.getNum();
    }

    public void handlerCarNum(int type, GoodsBean goodsBean) {
        if (type == 0) {
            GoodsBean temp = selectedList.get(goodsBean.getProductId());
            if (temp != null) {
                if (temp.getNum() < 2) {
                    goodsBean.setNum(0);
                    selectedList.remove(goodsBean.getProductId());

                } else {
                    int i = goodsBean.getNum();
                    goodsBean.setNum(--i);
                }
            }

        } else if (type == 1) {
            GoodsBean temp = selectedList.get(goodsBean.getProductId());
            if (temp == null) {
                goodsBean.setNum(1);
                selectedList.append(goodsBean.getProductId(), goodsBean);
            } else {
                int i = goodsBean.getNum();
                goodsBean.setNum(++i);
            }
        }

        update();
    }

    public void setAnim(int[] startLocation) {
        ImageView ball = new ImageView(this);
        ball.setImageResource(R.mipmap.one);
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(ball);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(ball, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        textCart.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                ball.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                ball.setVisibility(View.GONE);
            }
        });

    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
}
