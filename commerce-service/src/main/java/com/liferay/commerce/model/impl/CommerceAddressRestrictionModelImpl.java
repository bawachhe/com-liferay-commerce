/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommerceAddressRestrictionModel;
import com.liferay.commerce.model.CommerceAddressRestrictionSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CommerceAddressRestriction service. Represents a row in the &quot;CommerceAddressRestriction&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceAddressRestrictionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceAddressRestrictionImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionImpl
 * @see CommerceAddressRestriction
 * @see CommerceAddressRestrictionModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceAddressRestrictionModelImpl extends BaseModelImpl<CommerceAddressRestriction>
	implements CommerceAddressRestrictionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce address restriction model instance should use the {@link CommerceAddressRestriction} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceAddressRestriction";
	public static final Object[][] TABLE_COLUMNS = {
			{ "commerceAddressRestrictionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "commerceCountryId", Types.BIGINT }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceAddressRestrictionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceAddressRestriction (commerceAddressRestrictionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,commerceCountryId LONG)";
	public static final String TABLE_SQL_DROP = "drop table CommerceAddressRestriction";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceAddressRestriction.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceAddressRestriction.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.model.CommerceAddressRestriction"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.model.CommerceAddressRestriction"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.model.CommerceAddressRestriction"),
			true);
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static final long CLASSPK_COLUMN_BITMASK = 2L;
	public static final long COMMERCECOUNTRYID_COLUMN_BITMASK = 4L;
	public static final long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceAddressRestriction toModel(
		CommerceAddressRestrictionSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceAddressRestriction model = new CommerceAddressRestrictionImpl();

		model.setCommerceAddressRestrictionId(soapModel.getCommerceAddressRestrictionId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setCommerceCountryId(soapModel.getCommerceCountryId());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommerceAddressRestriction> toModels(
		CommerceAddressRestrictionSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceAddressRestriction> models = new ArrayList<CommerceAddressRestriction>(soapModels.length);

		for (CommerceAddressRestrictionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.model.CommerceAddressRestriction"));

	public CommerceAddressRestrictionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceAddressRestrictionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceAddressRestrictionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAddressRestrictionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAddressRestriction.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAddressRestriction.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceAddressRestrictionId",
			getCommerceAddressRestrictionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceCountryId", getCommerceCountryId());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceAddressRestrictionId = (Long)attributes.get(
				"commerceAddressRestrictionId");

		if (commerceAddressRestrictionId != null) {
			setCommerceAddressRestrictionId(commerceAddressRestrictionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
		}
	}

	@JSON
	@Override
	public long getCommerceAddressRestrictionId() {
		return _commerceAddressRestrictionId;
	}

	@Override
	public void setCommerceAddressRestrictionId(
		long commerceAddressRestrictionId) {
		_commerceAddressRestrictionId = commerceAddressRestrictionId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_columnBitmask |= COMMERCECOUNTRYID_COLUMN_BITMASK;

		if (!_setOriginalCommerceCountryId) {
			_setOriginalCommerceCountryId = true;

			_originalCommerceCountryId = _commerceCountryId;
		}

		_commerceCountryId = commerceCountryId;
	}

	public long getOriginalCommerceCountryId() {
		return _originalCommerceCountryId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceAddressRestriction.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceAddressRestriction toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceAddressRestriction)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceAddressRestrictionImpl commerceAddressRestrictionImpl = new CommerceAddressRestrictionImpl();

		commerceAddressRestrictionImpl.setCommerceAddressRestrictionId(getCommerceAddressRestrictionId());
		commerceAddressRestrictionImpl.setGroupId(getGroupId());
		commerceAddressRestrictionImpl.setCompanyId(getCompanyId());
		commerceAddressRestrictionImpl.setUserId(getUserId());
		commerceAddressRestrictionImpl.setUserName(getUserName());
		commerceAddressRestrictionImpl.setCreateDate(getCreateDate());
		commerceAddressRestrictionImpl.setModifiedDate(getModifiedDate());
		commerceAddressRestrictionImpl.setClassNameId(getClassNameId());
		commerceAddressRestrictionImpl.setClassPK(getClassPK());
		commerceAddressRestrictionImpl.setCommerceCountryId(getCommerceCountryId());

		commerceAddressRestrictionImpl.resetOriginalValues();

		return commerceAddressRestrictionImpl;
	}

	@Override
	public int compareTo(CommerceAddressRestriction commerceAddressRestriction) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				commerceAddressRestriction.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceAddressRestriction)) {
			return false;
		}

		CommerceAddressRestriction commerceAddressRestriction = (CommerceAddressRestriction)obj;

		long primaryKey = commerceAddressRestriction.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CommerceAddressRestrictionModelImpl commerceAddressRestrictionModelImpl = this;

		commerceAddressRestrictionModelImpl._setModifiedDate = false;

		commerceAddressRestrictionModelImpl._originalClassNameId = commerceAddressRestrictionModelImpl._classNameId;

		commerceAddressRestrictionModelImpl._setOriginalClassNameId = false;

		commerceAddressRestrictionModelImpl._originalClassPK = commerceAddressRestrictionModelImpl._classPK;

		commerceAddressRestrictionModelImpl._setOriginalClassPK = false;

		commerceAddressRestrictionModelImpl._originalCommerceCountryId = commerceAddressRestrictionModelImpl._commerceCountryId;

		commerceAddressRestrictionModelImpl._setOriginalCommerceCountryId = false;

		commerceAddressRestrictionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceAddressRestriction> toCacheModel() {
		CommerceAddressRestrictionCacheModel commerceAddressRestrictionCacheModel =
			new CommerceAddressRestrictionCacheModel();

		commerceAddressRestrictionCacheModel.commerceAddressRestrictionId = getCommerceAddressRestrictionId();

		commerceAddressRestrictionCacheModel.groupId = getGroupId();

		commerceAddressRestrictionCacheModel.companyId = getCompanyId();

		commerceAddressRestrictionCacheModel.userId = getUserId();

		commerceAddressRestrictionCacheModel.userName = getUserName();

		String userName = commerceAddressRestrictionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceAddressRestrictionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceAddressRestrictionCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceAddressRestrictionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceAddressRestrictionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceAddressRestrictionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceAddressRestrictionCacheModel.classNameId = getClassNameId();

		commerceAddressRestrictionCacheModel.classPK = getClassPK();

		commerceAddressRestrictionCacheModel.commerceCountryId = getCommerceCountryId();

		return commerceAddressRestrictionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceAddressRestrictionId=");
		sb.append(getCommerceAddressRestrictionId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", commerceCountryId=");
		sb.append(getCommerceCountryId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.commerce.model.CommerceAddressRestriction");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceAddressRestrictionId</column-name><column-value><![CDATA[");
		sb.append(getCommerceAddressRestrictionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>commerceCountryId</column-name><column-value><![CDATA[");
		sb.append(getCommerceCountryId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceAddressRestriction.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceAddressRestriction.class, ModelWrapper.class
		};
	private long _commerceAddressRestrictionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _commerceCountryId;
	private long _originalCommerceCountryId;
	private boolean _setOriginalCommerceCountryId;
	private long _columnBitmask;
	private CommerceAddressRestriction _escapedModel;
}