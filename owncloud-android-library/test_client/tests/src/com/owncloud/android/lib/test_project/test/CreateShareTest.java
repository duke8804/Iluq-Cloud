/* ownCloud Android Library is available under MIT license
 *   Copyright (C) 2015 ownCloud Inc.
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 *   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN 
 *   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 *   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */

package com.iluq_cloud.android.lib.test_project.test;

import java.io.File;

import com.iluq_cloud.android.lib.common.operations.RemoteOperationResult;
import com.iluq_cloud.android.lib.common.operations.RemoteOperationResult.ResultCode;
import com.iluq_cloud.android.lib.resources.shares.ShareType;
import com.iluq_cloud.android.lib.test_project.TestActivity;

public class CreateShareTest extends RemoteTest {

	private static final String LOG_TAG = CreateShareTest.class.getCanonicalName();
	
	/* File to share.*/
	private static final String FILE_TO_SHARE = "/fileToShare.txt";

	private TestActivity mActivity;
	private String mFullPath2FileToShare;
	
	@Override
	protected void setUp() throws Exception {
	    super.setUp();
	    setActivityInitialTouchMode(false);
	    mActivity = getActivity();
	    mFullPath2FileToShare = mBaseFolderPath + FILE_TO_SHARE;  
	    		
		File textFile = mActivity.extractAsset(TestActivity.ASSETS__TEXT_FILE_NAME);
		RemoteOperationResult result = mActivity.uploadFile(
				textFile.getAbsolutePath(), 
				mFullPath2FileToShare, 
				"txt/plain");
		if (!result.isSuccess()) {
			Utils.logAndThrow(LOG_TAG, result);
		}
	}
	
	/**
	 * Test Create Share: the server must support SHARE API
	 */
	public void testCreatePublicShare() {
		RemoteOperationResult result = mActivity.createShare(
				mFullPath2FileToShare, 
				ShareType.PUBLIC_LINK, 
				"", 
				false, 
				"", 
				1);
		assertTrue(result.isSuccess());
	}
	
	
	@Override
	protected void tearDown() throws Exception {
		RemoteOperationResult removeResult = mActivity.removeFile(mFullPath2FileToShare);
		if (!removeResult.isSuccess()  && removeResult.getCode() != ResultCode.TIMEOUT) {
			Utils.logAndThrow(LOG_TAG, removeResult);
		}
		super.tearDown();
	}
	
	
}
