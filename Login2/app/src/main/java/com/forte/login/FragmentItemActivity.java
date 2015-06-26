package com.forte.login;

import java.util.UUID;

import javax.sql.DataSource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forte.login.CarBrand;
import com.forte.login.SubModel;
import com.forte.carbrowser.database.CarBrowserDataSource;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FragmentItemActivity extends SherlockFragmentActivity {

	CarPagerAdapter carPagerAdapter;

	ViewPager myViewPager;
	
	private CarBrowserDataSource dataSource;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_item);
		Bundle extras = getIntent().getExtras();
		// getActionBar().setIcon(R.drawable.bmw_logo);
		// getSupportActionBar().setIcon(R.drawable.bmw_logo);
		long carBrandId = extras.getInt("BrandIndex");
		
		CarBrand carBrand = dataSource.getCarBrand(carBrandId);
		
		getSupportActionBar().setIcon(Integer.parseInt(carBrand.icon));
		getSupportActionBar().setTitle(carBrand.name);
		
		//getSupportActionBar().setIcon(Integer.parseInt(dataSource.getIcon(carBrandId)));
		//getSupportActionBar().setTitle(dataSource.getName(carBrandId));
		
		// getSupportActionBar().setTitle("BMW");

		carPagerAdapter = new CarPagerAdapter(getSupportFragmentManager(), extras.getInt("BrandIndex"));
		myViewPager = (ViewPager) findViewById(R.id.pager);
		myViewPager.setOffscreenPageLimit(0);
		myViewPager.setAdapter(carPagerAdapter);
		myViewPager.setCurrentItem(extras.getInt("ModelIndex"), true);
		myViewPager.destroyDrawingCache();
		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public static class CarPagerAdapter extends FragmentStatePagerAdapter {
		int brandIndex;

		public CarPagerAdapter(FragmentManager fm, int brandIndex) {
			super(fm);
			this.brandIndex = brandIndex;
		}

		@Override
		public int getCount() {
			return CarBrandsCollection.CAR_BRANDS[brandIndex].models.length;
		}

		@Override
		public Fragment getItem(int position) {
			// getSupportActionBar().setIcon(R.drawable.bmw_logo);
			return CarTabHostFragment.newInstance(brandIndex, position);
			// return CarDetailsItemFragment.newInstance(brandIndex, position);
		}
	}

	public static class CarDetailsItemFragment extends Fragment {
		// private FragmentTabHost myTabHost;

		int brandIndex;
		int modelIndex;
		int subModelIndex;

		static CarDetailsItemFragment newInstance(int brandIndex, int modelIndex, int subModelIndex) {
			CarDetailsItemFragment detailsItemFragment = new CarDetailsItemFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("brandIndex", brandIndex);
			args.putInt("modelIndex", modelIndex);
			args.putInt("subModelIndex", subModelIndex);
			detailsItemFragment.setArguments(args);

			return detailsItemFragment;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			brandIndex = getArguments() != null ? getArguments().getInt("brandIndex") : 1;
			modelIndex = getArguments() != null ? getArguments().getInt("modelIndex") : 1;
			subModelIndex = getArguments() != null ? getArguments().getInt("subModelIndex") : 1;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// myTabHost = new FragmentTabHost(getActivity());

			return inflater.inflate(R.layout.car_details_item_fragment, container, false);
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			
			View header = view.findViewById(R.id.carDetailsHeader);
			CarBrand carBrand = CarBrandsCollection.CAR_BRANDS[brandIndex];
			CarModel carModel = carBrand.models[modelIndex];
			SubModel subModel = carModel.subModels[subModelIndex];
			
			((TextView) header).setText(subModel.header);
			View details = view.findViewById(R.id.carDetailsDescription);
			((TextView) details).setText(subModel.description);
			View classification = view.findViewById(R.id.carClassification);
			((TextView) classification).setText(subModel.classification);
			// if (carModel.image != 0) {
			View image = view.findViewById(R.id.carDetailsImage);
			((ImageView) image).setImageDrawable(view.getResources().getDrawable(Integer.parseInt(subModel.image)));
			view.destroyDrawingCache();
			view.refreshDrawableState();
			view = null;
			// }
			
		}
		
		@Override
		public void onDestroyView() {
			super.onDestroyView();
		}
		@Override
		public void onDestroy() {
			super.onDestroy();
		}
		 
	}

	public static class CarTabHostFragment extends Fragment {
		private FragmentTabHost carTabHost;

		int brandIndex;
		int modelIndex;

		static CarTabHostFragment newInstance(int brandIndex, int modelIndex) {
			CarTabHostFragment tabFragment = new CarTabHostFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("brandIndex", brandIndex);
			args.putInt("modelIndex", modelIndex);
			tabFragment.setArguments(args);

			return tabFragment;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			brandIndex = getArguments() != null ? getArguments().getInt("brandIndex") : 1;
			modelIndex = getArguments() != null ? getArguments().getInt("modelIndex") : 1;

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			carTabHost = new FragmentTabHost(getActivity());
			carTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_tabhost);

			//Bundle args = new Bundle();
			//args.putInt("brandIndex", brandIndex);
			//args.putInt("modelIndex", modelIndex);

			// Bundle arg1 = new Bundle();
			// arg1.putInt(TextViewFragment.POSITION_KEY, 1);
			for (int i = 0; i < CarBrandsCollection.CAR_BRANDS[brandIndex].models[modelIndex].subModels.length; i++) {

				//carTabHost.addTab(carTabHost.newTabSpec("TabHostTextView1").setIndicator("1-series" + UUID.randomUUID()),
						//CarDetailsItemFragment.class, args);

				Bundle args2 = new Bundle();
				args2.putInt("brandIndex", brandIndex);
				args2.putInt("modelIndex", modelIndex);
				args2.putInt("subModelIndex", i);
								
				SubModel subModel = CarBrandsCollection.CAR_BRANDS[brandIndex].models[modelIndex].subModels[i];

				carTabHost.addTab(carTabHost.newTabSpec(subModel.tabHeader).setIndicator(subModel.tabHeader),
						CarDetailsItemFragment.class, args2);
				//carTabHost.updateViewLayout(container, container.getLayoutParams());
				//carTabHost.destroyDrawingCache();
			}
			//carTabHost.clearAllTabs();
			//carTabHost.updateViewLayout(container, container.getLayoutParams());
			// Bundle arg2 = new Bundle();
			// arg2.putInt(TextViewFragment.POSITION_KEY, 2);
			// mTabHost.addTab(mTabHost.newTabSpec("TabHostTextView2").setIndicator("Child 2"),
			// TextViewFragment.class, arg2);

			return carTabHost;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			carTabHost = null;
		}
		@Override
		public void onDestroy() {
			super.onDestroy();
			carTabHost = null;
		}
	}

}
