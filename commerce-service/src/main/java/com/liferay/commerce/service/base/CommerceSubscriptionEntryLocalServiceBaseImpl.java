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

package com.liferay.commerce.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.service.persistence.CPDAvailabilityEstimatePersistence;
import com.liferay.commerce.service.persistence.CPDefinitionInventoryPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressRestrictionPersistence;
import com.liferay.commerce.service.persistence.CommerceAvailabilityEstimatePersistence;
import com.liferay.commerce.service.persistence.CommerceCountryFinder;
import com.liferay.commerce.service.persistence.CommerceCountryPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderFinder;
import com.liferay.commerce.service.persistence.CommerceOrderItemFinder;
import com.liferay.commerce.service.persistence.CommerceOrderItemPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderNotePersistence;
import com.liferay.commerce.service.persistence.CommerceOrderPaymentPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.commerce.service.persistence.CommercePaymentMethodPersistence;
import com.liferay.commerce.service.persistence.CommerceRegionPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentItemFinder;
import com.liferay.commerce.service.persistence.CommerceShipmentItemPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentPersistence;
import com.liferay.commerce.service.persistence.CommerceShippingMethodPersistence;
import com.liferay.commerce.service.persistence.CommerceSubscriptionCycleEntryPersistence;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryFinder;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryPersistence;
import com.liferay.commerce.service.persistence.CommerceWarehouseFinder;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemFinder;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemPersistence;
import com.liferay.commerce.service.persistence.CommerceWarehousePersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the commerce subscription entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.service.impl.CommerceSubscriptionEntryLocalServiceImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.impl.CommerceSubscriptionEntryLocalServiceImpl
 * @see com.liferay.commerce.service.CommerceSubscriptionEntryLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class CommerceSubscriptionEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceSubscriptionEntryLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.commerce.service.CommerceSubscriptionEntryLocalServiceUtil} to access the commerce subscription entry local service.
	 */

	/**
	 * Adds the commerce subscription entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceSubscriptionEntry the commerce subscription entry
	 * @return the commerce subscription entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceSubscriptionEntry addCommerceSubscriptionEntry(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {
		commerceSubscriptionEntry.setNew(true);

		return commerceSubscriptionEntryPersistence.update(commerceSubscriptionEntry);
	}

	/**
	 * Creates a new commerce subscription entry with the primary key. Does not add the commerce subscription entry to the database.
	 *
	 * @param commerceSubscriptionEntryId the primary key for the new commerce subscription entry
	 * @return the new commerce subscription entry
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceSubscriptionEntry createCommerceSubscriptionEntry(
		long commerceSubscriptionEntryId) {
		return commerceSubscriptionEntryPersistence.create(commerceSubscriptionEntryId);
	}

	/**
	 * Deletes the commerce subscription entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry that was removed
	 * @throws PortalException if a commerce subscription entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceSubscriptionEntry deleteCommerceSubscriptionEntry(
		long commerceSubscriptionEntryId) throws PortalException {
		return commerceSubscriptionEntryPersistence.remove(commerceSubscriptionEntryId);
	}

	/**
	 * Deletes the commerce subscription entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceSubscriptionEntry the commerce subscription entry
	 * @return the commerce subscription entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceSubscriptionEntry deleteCommerceSubscriptionEntry(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {
		return commerceSubscriptionEntryPersistence.remove(commerceSubscriptionEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CommerceSubscriptionEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceSubscriptionEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return commerceSubscriptionEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return commerceSubscriptionEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceSubscriptionEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return commerceSubscriptionEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CommerceSubscriptionEntry fetchCommerceSubscriptionEntry(
		long commerceSubscriptionEntryId) {
		return commerceSubscriptionEntryPersistence.fetchByPrimaryKey(commerceSubscriptionEntryId);
	}

	/**
	 * Returns the commerce subscription entry matching the UUID and group.
	 *
	 * @param uuid the commerce subscription entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchCommerceSubscriptionEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return commerceSubscriptionEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce subscription entry with the primary key.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry
	 * @throws PortalException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry getCommerceSubscriptionEntry(
		long commerceSubscriptionEntryId) throws PortalException {
		return commerceSubscriptionEntryPersistence.findByPrimaryKey(commerceSubscriptionEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(commerceSubscriptionEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceSubscriptionEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceSubscriptionEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(commerceSubscriptionEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CommerceSubscriptionEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceSubscriptionEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(commerceSubscriptionEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceSubscriptionEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceSubscriptionEntryId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceSubscriptionEntry>() {
				@Override
				public void performAction(
					CommerceSubscriptionEntry commerceSubscriptionEntry)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						commerceSubscriptionEntry);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(
					CommerceSubscriptionEntry.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return commerceSubscriptionEntryLocalService.deleteCommerceSubscriptionEntry((CommerceSubscriptionEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return commerceSubscriptionEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the commerce subscription entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce subscription entries
	 * @param companyId the primary key of the company
	 * @return the matching commerce subscription entries, or an empty list if no matches were found
	 */
	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return commerceSubscriptionEntryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of commerce subscription entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce subscription entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching commerce subscription entries, or an empty list if no matches were found
	 */
	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {
		return commerceSubscriptionEntryPersistence.findByUuid_C(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce subscription entry matching the UUID and group.
	 *
	 * @param uuid the commerce subscription entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce subscription entry
	 * @throws PortalException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry getCommerceSubscriptionEntryByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return commerceSubscriptionEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntries(
		int start, int end) {
		return commerceSubscriptionEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce subscription entries.
	 *
	 * @return the number of commerce subscription entries
	 */
	@Override
	public int getCommerceSubscriptionEntriesCount() {
		return commerceSubscriptionEntryPersistence.countAll();
	}

	/**
	 * Updates the commerce subscription entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param commerceSubscriptionEntry the commerce subscription entry
	 * @return the commerce subscription entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceSubscriptionEntry updateCommerceSubscriptionEntry(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {
		return commerceSubscriptionEntryPersistence.update(commerceSubscriptionEntry);
	}

	/**
	 * Returns the commerce address local service.
	 *
	 * @return the commerce address local service
	 */
	public com.liferay.commerce.service.CommerceAddressLocalService getCommerceAddressLocalService() {
		return commerceAddressLocalService;
	}

	/**
	 * Sets the commerce address local service.
	 *
	 * @param commerceAddressLocalService the commerce address local service
	 */
	public void setCommerceAddressLocalService(
		com.liferay.commerce.service.CommerceAddressLocalService commerceAddressLocalService) {
		this.commerceAddressLocalService = commerceAddressLocalService;
	}

	/**
	 * Returns the commerce address persistence.
	 *
	 * @return the commerce address persistence
	 */
	public CommerceAddressPersistence getCommerceAddressPersistence() {
		return commerceAddressPersistence;
	}

	/**
	 * Sets the commerce address persistence.
	 *
	 * @param commerceAddressPersistence the commerce address persistence
	 */
	public void setCommerceAddressPersistence(
		CommerceAddressPersistence commerceAddressPersistence) {
		this.commerceAddressPersistence = commerceAddressPersistence;
	}

	/**
	 * Returns the commerce address restriction local service.
	 *
	 * @return the commerce address restriction local service
	 */
	public com.liferay.commerce.service.CommerceAddressRestrictionLocalService getCommerceAddressRestrictionLocalService() {
		return commerceAddressRestrictionLocalService;
	}

	/**
	 * Sets the commerce address restriction local service.
	 *
	 * @param commerceAddressRestrictionLocalService the commerce address restriction local service
	 */
	public void setCommerceAddressRestrictionLocalService(
		com.liferay.commerce.service.CommerceAddressRestrictionLocalService commerceAddressRestrictionLocalService) {
		this.commerceAddressRestrictionLocalService = commerceAddressRestrictionLocalService;
	}

	/**
	 * Returns the commerce address restriction persistence.
	 *
	 * @return the commerce address restriction persistence
	 */
	public CommerceAddressRestrictionPersistence getCommerceAddressRestrictionPersistence() {
		return commerceAddressRestrictionPersistence;
	}

	/**
	 * Sets the commerce address restriction persistence.
	 *
	 * @param commerceAddressRestrictionPersistence the commerce address restriction persistence
	 */
	public void setCommerceAddressRestrictionPersistence(
		CommerceAddressRestrictionPersistence commerceAddressRestrictionPersistence) {
		this.commerceAddressRestrictionPersistence = commerceAddressRestrictionPersistence;
	}

	/**
	 * Returns the commerce availability estimate local service.
	 *
	 * @return the commerce availability estimate local service
	 */
	public com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService getCommerceAvailabilityEstimateLocalService() {
		return commerceAvailabilityEstimateLocalService;
	}

	/**
	 * Sets the commerce availability estimate local service.
	 *
	 * @param commerceAvailabilityEstimateLocalService the commerce availability estimate local service
	 */
	public void setCommerceAvailabilityEstimateLocalService(
		com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService commerceAvailabilityEstimateLocalService) {
		this.commerceAvailabilityEstimateLocalService = commerceAvailabilityEstimateLocalService;
	}

	/**
	 * Returns the commerce availability estimate persistence.
	 *
	 * @return the commerce availability estimate persistence
	 */
	public CommerceAvailabilityEstimatePersistence getCommerceAvailabilityEstimatePersistence() {
		return commerceAvailabilityEstimatePersistence;
	}

	/**
	 * Sets the commerce availability estimate persistence.
	 *
	 * @param commerceAvailabilityEstimatePersistence the commerce availability estimate persistence
	 */
	public void setCommerceAvailabilityEstimatePersistence(
		CommerceAvailabilityEstimatePersistence commerceAvailabilityEstimatePersistence) {
		this.commerceAvailabilityEstimatePersistence = commerceAvailabilityEstimatePersistence;
	}

	/**
	 * Returns the commerce country local service.
	 *
	 * @return the commerce country local service
	 */
	public com.liferay.commerce.service.CommerceCountryLocalService getCommerceCountryLocalService() {
		return commerceCountryLocalService;
	}

	/**
	 * Sets the commerce country local service.
	 *
	 * @param commerceCountryLocalService the commerce country local service
	 */
	public void setCommerceCountryLocalService(
		com.liferay.commerce.service.CommerceCountryLocalService commerceCountryLocalService) {
		this.commerceCountryLocalService = commerceCountryLocalService;
	}

	/**
	 * Returns the commerce country persistence.
	 *
	 * @return the commerce country persistence
	 */
	public CommerceCountryPersistence getCommerceCountryPersistence() {
		return commerceCountryPersistence;
	}

	/**
	 * Sets the commerce country persistence.
	 *
	 * @param commerceCountryPersistence the commerce country persistence
	 */
	public void setCommerceCountryPersistence(
		CommerceCountryPersistence commerceCountryPersistence) {
		this.commerceCountryPersistence = commerceCountryPersistence;
	}

	/**
	 * Returns the commerce country finder.
	 *
	 * @return the commerce country finder
	 */
	public CommerceCountryFinder getCommerceCountryFinder() {
		return commerceCountryFinder;
	}

	/**
	 * Sets the commerce country finder.
	 *
	 * @param commerceCountryFinder the commerce country finder
	 */
	public void setCommerceCountryFinder(
		CommerceCountryFinder commerceCountryFinder) {
		this.commerceCountryFinder = commerceCountryFinder;
	}

	/**
	 * Returns the commerce order local service.
	 *
	 * @return the commerce order local service
	 */
	public com.liferay.commerce.service.CommerceOrderLocalService getCommerceOrderLocalService() {
		return commerceOrderLocalService;
	}

	/**
	 * Sets the commerce order local service.
	 *
	 * @param commerceOrderLocalService the commerce order local service
	 */
	public void setCommerceOrderLocalService(
		com.liferay.commerce.service.CommerceOrderLocalService commerceOrderLocalService) {
		this.commerceOrderLocalService = commerceOrderLocalService;
	}

	/**
	 * Returns the commerce order persistence.
	 *
	 * @return the commerce order persistence
	 */
	public CommerceOrderPersistence getCommerceOrderPersistence() {
		return commerceOrderPersistence;
	}

	/**
	 * Sets the commerce order persistence.
	 *
	 * @param commerceOrderPersistence the commerce order persistence
	 */
	public void setCommerceOrderPersistence(
		CommerceOrderPersistence commerceOrderPersistence) {
		this.commerceOrderPersistence = commerceOrderPersistence;
	}

	/**
	 * Returns the commerce order finder.
	 *
	 * @return the commerce order finder
	 */
	public CommerceOrderFinder getCommerceOrderFinder() {
		return commerceOrderFinder;
	}

	/**
	 * Sets the commerce order finder.
	 *
	 * @param commerceOrderFinder the commerce order finder
	 */
	public void setCommerceOrderFinder(CommerceOrderFinder commerceOrderFinder) {
		this.commerceOrderFinder = commerceOrderFinder;
	}

	/**
	 * Returns the commerce order item local service.
	 *
	 * @return the commerce order item local service
	 */
	public com.liferay.commerce.service.CommerceOrderItemLocalService getCommerceOrderItemLocalService() {
		return commerceOrderItemLocalService;
	}

	/**
	 * Sets the commerce order item local service.
	 *
	 * @param commerceOrderItemLocalService the commerce order item local service
	 */
	public void setCommerceOrderItemLocalService(
		com.liferay.commerce.service.CommerceOrderItemLocalService commerceOrderItemLocalService) {
		this.commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	/**
	 * Returns the commerce order item persistence.
	 *
	 * @return the commerce order item persistence
	 */
	public CommerceOrderItemPersistence getCommerceOrderItemPersistence() {
		return commerceOrderItemPersistence;
	}

	/**
	 * Sets the commerce order item persistence.
	 *
	 * @param commerceOrderItemPersistence the commerce order item persistence
	 */
	public void setCommerceOrderItemPersistence(
		CommerceOrderItemPersistence commerceOrderItemPersistence) {
		this.commerceOrderItemPersistence = commerceOrderItemPersistence;
	}

	/**
	 * Returns the commerce order item finder.
	 *
	 * @return the commerce order item finder
	 */
	public CommerceOrderItemFinder getCommerceOrderItemFinder() {
		return commerceOrderItemFinder;
	}

	/**
	 * Sets the commerce order item finder.
	 *
	 * @param commerceOrderItemFinder the commerce order item finder
	 */
	public void setCommerceOrderItemFinder(
		CommerceOrderItemFinder commerceOrderItemFinder) {
		this.commerceOrderItemFinder = commerceOrderItemFinder;
	}

	/**
	 * Returns the commerce order note local service.
	 *
	 * @return the commerce order note local service
	 */
	public com.liferay.commerce.service.CommerceOrderNoteLocalService getCommerceOrderNoteLocalService() {
		return commerceOrderNoteLocalService;
	}

	/**
	 * Sets the commerce order note local service.
	 *
	 * @param commerceOrderNoteLocalService the commerce order note local service
	 */
	public void setCommerceOrderNoteLocalService(
		com.liferay.commerce.service.CommerceOrderNoteLocalService commerceOrderNoteLocalService) {
		this.commerceOrderNoteLocalService = commerceOrderNoteLocalService;
	}

	/**
	 * Returns the commerce order note persistence.
	 *
	 * @return the commerce order note persistence
	 */
	public CommerceOrderNotePersistence getCommerceOrderNotePersistence() {
		return commerceOrderNotePersistence;
	}

	/**
	 * Sets the commerce order note persistence.
	 *
	 * @param commerceOrderNotePersistence the commerce order note persistence
	 */
	public void setCommerceOrderNotePersistence(
		CommerceOrderNotePersistence commerceOrderNotePersistence) {
		this.commerceOrderNotePersistence = commerceOrderNotePersistence;
	}

	/**
	 * Returns the commerce order payment local service.
	 *
	 * @return the commerce order payment local service
	 */
	public com.liferay.commerce.service.CommerceOrderPaymentLocalService getCommerceOrderPaymentLocalService() {
		return commerceOrderPaymentLocalService;
	}

	/**
	 * Sets the commerce order payment local service.
	 *
	 * @param commerceOrderPaymentLocalService the commerce order payment local service
	 */
	public void setCommerceOrderPaymentLocalService(
		com.liferay.commerce.service.CommerceOrderPaymentLocalService commerceOrderPaymentLocalService) {
		this.commerceOrderPaymentLocalService = commerceOrderPaymentLocalService;
	}

	/**
	 * Returns the commerce order payment persistence.
	 *
	 * @return the commerce order payment persistence
	 */
	public CommerceOrderPaymentPersistence getCommerceOrderPaymentPersistence() {
		return commerceOrderPaymentPersistence;
	}

	/**
	 * Sets the commerce order payment persistence.
	 *
	 * @param commerceOrderPaymentPersistence the commerce order payment persistence
	 */
	public void setCommerceOrderPaymentPersistence(
		CommerceOrderPaymentPersistence commerceOrderPaymentPersistence) {
		this.commerceOrderPaymentPersistence = commerceOrderPaymentPersistence;
	}

	/**
	 * Returns the commerce payment method local service.
	 *
	 * @return the commerce payment method local service
	 */
	public com.liferay.commerce.service.CommercePaymentMethodLocalService getCommercePaymentMethodLocalService() {
		return commercePaymentMethodLocalService;
	}

	/**
	 * Sets the commerce payment method local service.
	 *
	 * @param commercePaymentMethodLocalService the commerce payment method local service
	 */
	public void setCommercePaymentMethodLocalService(
		com.liferay.commerce.service.CommercePaymentMethodLocalService commercePaymentMethodLocalService) {
		this.commercePaymentMethodLocalService = commercePaymentMethodLocalService;
	}

	/**
	 * Returns the commerce payment method persistence.
	 *
	 * @return the commerce payment method persistence
	 */
	public CommercePaymentMethodPersistence getCommercePaymentMethodPersistence() {
		return commercePaymentMethodPersistence;
	}

	/**
	 * Sets the commerce payment method persistence.
	 *
	 * @param commercePaymentMethodPersistence the commerce payment method persistence
	 */
	public void setCommercePaymentMethodPersistence(
		CommercePaymentMethodPersistence commercePaymentMethodPersistence) {
		this.commercePaymentMethodPersistence = commercePaymentMethodPersistence;
	}

	/**
	 * Returns the commerce region local service.
	 *
	 * @return the commerce region local service
	 */
	public com.liferay.commerce.service.CommerceRegionLocalService getCommerceRegionLocalService() {
		return commerceRegionLocalService;
	}

	/**
	 * Sets the commerce region local service.
	 *
	 * @param commerceRegionLocalService the commerce region local service
	 */
	public void setCommerceRegionLocalService(
		com.liferay.commerce.service.CommerceRegionLocalService commerceRegionLocalService) {
		this.commerceRegionLocalService = commerceRegionLocalService;
	}

	/**
	 * Returns the commerce region persistence.
	 *
	 * @return the commerce region persistence
	 */
	public CommerceRegionPersistence getCommerceRegionPersistence() {
		return commerceRegionPersistence;
	}

	/**
	 * Sets the commerce region persistence.
	 *
	 * @param commerceRegionPersistence the commerce region persistence
	 */
	public void setCommerceRegionPersistence(
		CommerceRegionPersistence commerceRegionPersistence) {
		this.commerceRegionPersistence = commerceRegionPersistence;
	}

	/**
	 * Returns the commerce shipment local service.
	 *
	 * @return the commerce shipment local service
	 */
	public com.liferay.commerce.service.CommerceShipmentLocalService getCommerceShipmentLocalService() {
		return commerceShipmentLocalService;
	}

	/**
	 * Sets the commerce shipment local service.
	 *
	 * @param commerceShipmentLocalService the commerce shipment local service
	 */
	public void setCommerceShipmentLocalService(
		com.liferay.commerce.service.CommerceShipmentLocalService commerceShipmentLocalService) {
		this.commerceShipmentLocalService = commerceShipmentLocalService;
	}

	/**
	 * Returns the commerce shipment persistence.
	 *
	 * @return the commerce shipment persistence
	 */
	public CommerceShipmentPersistence getCommerceShipmentPersistence() {
		return commerceShipmentPersistence;
	}

	/**
	 * Sets the commerce shipment persistence.
	 *
	 * @param commerceShipmentPersistence the commerce shipment persistence
	 */
	public void setCommerceShipmentPersistence(
		CommerceShipmentPersistence commerceShipmentPersistence) {
		this.commerceShipmentPersistence = commerceShipmentPersistence;
	}

	/**
	 * Returns the commerce shipment item local service.
	 *
	 * @return the commerce shipment item local service
	 */
	public com.liferay.commerce.service.CommerceShipmentItemLocalService getCommerceShipmentItemLocalService() {
		return commerceShipmentItemLocalService;
	}

	/**
	 * Sets the commerce shipment item local service.
	 *
	 * @param commerceShipmentItemLocalService the commerce shipment item local service
	 */
	public void setCommerceShipmentItemLocalService(
		com.liferay.commerce.service.CommerceShipmentItemLocalService commerceShipmentItemLocalService) {
		this.commerceShipmentItemLocalService = commerceShipmentItemLocalService;
	}

	/**
	 * Returns the commerce shipment item persistence.
	 *
	 * @return the commerce shipment item persistence
	 */
	public CommerceShipmentItemPersistence getCommerceShipmentItemPersistence() {
		return commerceShipmentItemPersistence;
	}

	/**
	 * Sets the commerce shipment item persistence.
	 *
	 * @param commerceShipmentItemPersistence the commerce shipment item persistence
	 */
	public void setCommerceShipmentItemPersistence(
		CommerceShipmentItemPersistence commerceShipmentItemPersistence) {
		this.commerceShipmentItemPersistence = commerceShipmentItemPersistence;
	}

	/**
	 * Returns the commerce shipment item finder.
	 *
	 * @return the commerce shipment item finder
	 */
	public CommerceShipmentItemFinder getCommerceShipmentItemFinder() {
		return commerceShipmentItemFinder;
	}

	/**
	 * Sets the commerce shipment item finder.
	 *
	 * @param commerceShipmentItemFinder the commerce shipment item finder
	 */
	public void setCommerceShipmentItemFinder(
		CommerceShipmentItemFinder commerceShipmentItemFinder) {
		this.commerceShipmentItemFinder = commerceShipmentItemFinder;
	}

	/**
	 * Returns the commerce shipping method local service.
	 *
	 * @return the commerce shipping method local service
	 */
	public com.liferay.commerce.service.CommerceShippingMethodLocalService getCommerceShippingMethodLocalService() {
		return commerceShippingMethodLocalService;
	}

	/**
	 * Sets the commerce shipping method local service.
	 *
	 * @param commerceShippingMethodLocalService the commerce shipping method local service
	 */
	public void setCommerceShippingMethodLocalService(
		com.liferay.commerce.service.CommerceShippingMethodLocalService commerceShippingMethodLocalService) {
		this.commerceShippingMethodLocalService = commerceShippingMethodLocalService;
	}

	/**
	 * Returns the commerce shipping method persistence.
	 *
	 * @return the commerce shipping method persistence
	 */
	public CommerceShippingMethodPersistence getCommerceShippingMethodPersistence() {
		return commerceShippingMethodPersistence;
	}

	/**
	 * Sets the commerce shipping method persistence.
	 *
	 * @param commerceShippingMethodPersistence the commerce shipping method persistence
	 */
	public void setCommerceShippingMethodPersistence(
		CommerceShippingMethodPersistence commerceShippingMethodPersistence) {
		this.commerceShippingMethodPersistence = commerceShippingMethodPersistence;
	}

	/**
	 * Returns the commerce subscription cycle entry local service.
	 *
	 * @return the commerce subscription cycle entry local service
	 */
	public com.liferay.commerce.service.CommerceSubscriptionCycleEntryLocalService getCommerceSubscriptionCycleEntryLocalService() {
		return commerceSubscriptionCycleEntryLocalService;
	}

	/**
	 * Sets the commerce subscription cycle entry local service.
	 *
	 * @param commerceSubscriptionCycleEntryLocalService the commerce subscription cycle entry local service
	 */
	public void setCommerceSubscriptionCycleEntryLocalService(
		com.liferay.commerce.service.CommerceSubscriptionCycleEntryLocalService commerceSubscriptionCycleEntryLocalService) {
		this.commerceSubscriptionCycleEntryLocalService = commerceSubscriptionCycleEntryLocalService;
	}

	/**
	 * Returns the commerce subscription cycle entry persistence.
	 *
	 * @return the commerce subscription cycle entry persistence
	 */
	public CommerceSubscriptionCycleEntryPersistence getCommerceSubscriptionCycleEntryPersistence() {
		return commerceSubscriptionCycleEntryPersistence;
	}

	/**
	 * Sets the commerce subscription cycle entry persistence.
	 *
	 * @param commerceSubscriptionCycleEntryPersistence the commerce subscription cycle entry persistence
	 */
	public void setCommerceSubscriptionCycleEntryPersistence(
		CommerceSubscriptionCycleEntryPersistence commerceSubscriptionCycleEntryPersistence) {
		this.commerceSubscriptionCycleEntryPersistence = commerceSubscriptionCycleEntryPersistence;
	}

	/**
	 * Returns the commerce subscription entry local service.
	 *
	 * @return the commerce subscription entry local service
	 */
	public CommerceSubscriptionEntryLocalService getCommerceSubscriptionEntryLocalService() {
		return commerceSubscriptionEntryLocalService;
	}

	/**
	 * Sets the commerce subscription entry local service.
	 *
	 * @param commerceSubscriptionEntryLocalService the commerce subscription entry local service
	 */
	public void setCommerceSubscriptionEntryLocalService(
		CommerceSubscriptionEntryLocalService commerceSubscriptionEntryLocalService) {
		this.commerceSubscriptionEntryLocalService = commerceSubscriptionEntryLocalService;
	}

	/**
	 * Returns the commerce subscription entry persistence.
	 *
	 * @return the commerce subscription entry persistence
	 */
	public CommerceSubscriptionEntryPersistence getCommerceSubscriptionEntryPersistence() {
		return commerceSubscriptionEntryPersistence;
	}

	/**
	 * Sets the commerce subscription entry persistence.
	 *
	 * @param commerceSubscriptionEntryPersistence the commerce subscription entry persistence
	 */
	public void setCommerceSubscriptionEntryPersistence(
		CommerceSubscriptionEntryPersistence commerceSubscriptionEntryPersistence) {
		this.commerceSubscriptionEntryPersistence = commerceSubscriptionEntryPersistence;
	}

	/**
	 * Returns the commerce subscription entry finder.
	 *
	 * @return the commerce subscription entry finder
	 */
	public CommerceSubscriptionEntryFinder getCommerceSubscriptionEntryFinder() {
		return commerceSubscriptionEntryFinder;
	}

	/**
	 * Sets the commerce subscription entry finder.
	 *
	 * @param commerceSubscriptionEntryFinder the commerce subscription entry finder
	 */
	public void setCommerceSubscriptionEntryFinder(
		CommerceSubscriptionEntryFinder commerceSubscriptionEntryFinder) {
		this.commerceSubscriptionEntryFinder = commerceSubscriptionEntryFinder;
	}

	/**
	 * Returns the commerce warehouse local service.
	 *
	 * @return the commerce warehouse local service
	 */
	public com.liferay.commerce.service.CommerceWarehouseLocalService getCommerceWarehouseLocalService() {
		return commerceWarehouseLocalService;
	}

	/**
	 * Sets the commerce warehouse local service.
	 *
	 * @param commerceWarehouseLocalService the commerce warehouse local service
	 */
	public void setCommerceWarehouseLocalService(
		com.liferay.commerce.service.CommerceWarehouseLocalService commerceWarehouseLocalService) {
		this.commerceWarehouseLocalService = commerceWarehouseLocalService;
	}

	/**
	 * Returns the commerce warehouse persistence.
	 *
	 * @return the commerce warehouse persistence
	 */
	public CommerceWarehousePersistence getCommerceWarehousePersistence() {
		return commerceWarehousePersistence;
	}

	/**
	 * Sets the commerce warehouse persistence.
	 *
	 * @param commerceWarehousePersistence the commerce warehouse persistence
	 */
	public void setCommerceWarehousePersistence(
		CommerceWarehousePersistence commerceWarehousePersistence) {
		this.commerceWarehousePersistence = commerceWarehousePersistence;
	}

	/**
	 * Returns the commerce warehouse finder.
	 *
	 * @return the commerce warehouse finder
	 */
	public CommerceWarehouseFinder getCommerceWarehouseFinder() {
		return commerceWarehouseFinder;
	}

	/**
	 * Sets the commerce warehouse finder.
	 *
	 * @param commerceWarehouseFinder the commerce warehouse finder
	 */
	public void setCommerceWarehouseFinder(
		CommerceWarehouseFinder commerceWarehouseFinder) {
		this.commerceWarehouseFinder = commerceWarehouseFinder;
	}

	/**
	 * Returns the commerce warehouse item local service.
	 *
	 * @return the commerce warehouse item local service
	 */
	public com.liferay.commerce.service.CommerceWarehouseItemLocalService getCommerceWarehouseItemLocalService() {
		return commerceWarehouseItemLocalService;
	}

	/**
	 * Sets the commerce warehouse item local service.
	 *
	 * @param commerceWarehouseItemLocalService the commerce warehouse item local service
	 */
	public void setCommerceWarehouseItemLocalService(
		com.liferay.commerce.service.CommerceWarehouseItemLocalService commerceWarehouseItemLocalService) {
		this.commerceWarehouseItemLocalService = commerceWarehouseItemLocalService;
	}

	/**
	 * Returns the commerce warehouse item persistence.
	 *
	 * @return the commerce warehouse item persistence
	 */
	public CommerceWarehouseItemPersistence getCommerceWarehouseItemPersistence() {
		return commerceWarehouseItemPersistence;
	}

	/**
	 * Sets the commerce warehouse item persistence.
	 *
	 * @param commerceWarehouseItemPersistence the commerce warehouse item persistence
	 */
	public void setCommerceWarehouseItemPersistence(
		CommerceWarehouseItemPersistence commerceWarehouseItemPersistence) {
		this.commerceWarehouseItemPersistence = commerceWarehouseItemPersistence;
	}

	/**
	 * Returns the commerce warehouse item finder.
	 *
	 * @return the commerce warehouse item finder
	 */
	public CommerceWarehouseItemFinder getCommerceWarehouseItemFinder() {
		return commerceWarehouseItemFinder;
	}

	/**
	 * Sets the commerce warehouse item finder.
	 *
	 * @param commerceWarehouseItemFinder the commerce warehouse item finder
	 */
	public void setCommerceWarehouseItemFinder(
		CommerceWarehouseItemFinder commerceWarehouseItemFinder) {
		this.commerceWarehouseItemFinder = commerceWarehouseItemFinder;
	}

	/**
	 * Returns the cpd availability estimate local service.
	 *
	 * @return the cpd availability estimate local service
	 */
	public com.liferay.commerce.service.CPDAvailabilityEstimateLocalService getCPDAvailabilityEstimateLocalService() {
		return cpdAvailabilityEstimateLocalService;
	}

	/**
	 * Sets the cpd availability estimate local service.
	 *
	 * @param cpdAvailabilityEstimateLocalService the cpd availability estimate local service
	 */
	public void setCPDAvailabilityEstimateLocalService(
		com.liferay.commerce.service.CPDAvailabilityEstimateLocalService cpdAvailabilityEstimateLocalService) {
		this.cpdAvailabilityEstimateLocalService = cpdAvailabilityEstimateLocalService;
	}

	/**
	 * Returns the cpd availability estimate persistence.
	 *
	 * @return the cpd availability estimate persistence
	 */
	public CPDAvailabilityEstimatePersistence getCPDAvailabilityEstimatePersistence() {
		return cpdAvailabilityEstimatePersistence;
	}

	/**
	 * Sets the cpd availability estimate persistence.
	 *
	 * @param cpdAvailabilityEstimatePersistence the cpd availability estimate persistence
	 */
	public void setCPDAvailabilityEstimatePersistence(
		CPDAvailabilityEstimatePersistence cpdAvailabilityEstimatePersistence) {
		this.cpdAvailabilityEstimatePersistence = cpdAvailabilityEstimatePersistence;
	}

	/**
	 * Returns the cp definition inventory local service.
	 *
	 * @return the cp definition inventory local service
	 */
	public com.liferay.commerce.service.CPDefinitionInventoryLocalService getCPDefinitionInventoryLocalService() {
		return cpDefinitionInventoryLocalService;
	}

	/**
	 * Sets the cp definition inventory local service.
	 *
	 * @param cpDefinitionInventoryLocalService the cp definition inventory local service
	 */
	public void setCPDefinitionInventoryLocalService(
		com.liferay.commerce.service.CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService) {
		this.cpDefinitionInventoryLocalService = cpDefinitionInventoryLocalService;
	}

	/**
	 * Returns the cp definition inventory persistence.
	 *
	 * @return the cp definition inventory persistence
	 */
	public CPDefinitionInventoryPersistence getCPDefinitionInventoryPersistence() {
		return cpDefinitionInventoryPersistence;
	}

	/**
	 * Sets the cp definition inventory persistence.
	 *
	 * @param cpDefinitionInventoryPersistence the cp definition inventory persistence
	 */
	public void setCPDefinitionInventoryPersistence(
		CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence) {
		this.cpDefinitionInventoryPersistence = cpDefinitionInventoryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.commerce.model.CommerceSubscriptionEntry",
			commerceSubscriptionEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.model.CommerceSubscriptionEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceSubscriptionEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceSubscriptionEntry.class;
	}

	protected String getModelClassName() {
		return CommerceSubscriptionEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = commerceSubscriptionEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.commerce.service.CommerceAddressLocalService.class)
	protected com.liferay.commerce.service.CommerceAddressLocalService commerceAddressLocalService;
	@BeanReference(type = CommerceAddressPersistence.class)
	protected CommerceAddressPersistence commerceAddressPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceAddressRestrictionLocalService.class)
	protected com.liferay.commerce.service.CommerceAddressRestrictionLocalService commerceAddressRestrictionLocalService;
	@BeanReference(type = CommerceAddressRestrictionPersistence.class)
	protected CommerceAddressRestrictionPersistence commerceAddressRestrictionPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService.class)
	protected com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService commerceAvailabilityEstimateLocalService;
	@BeanReference(type = CommerceAvailabilityEstimatePersistence.class)
	protected CommerceAvailabilityEstimatePersistence commerceAvailabilityEstimatePersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceCountryLocalService.class)
	protected com.liferay.commerce.service.CommerceCountryLocalService commerceCountryLocalService;
	@BeanReference(type = CommerceCountryPersistence.class)
	protected CommerceCountryPersistence commerceCountryPersistence;
	@BeanReference(type = CommerceCountryFinder.class)
	protected CommerceCountryFinder commerceCountryFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderLocalService commerceOrderLocalService;
	@BeanReference(type = CommerceOrderPersistence.class)
	protected CommerceOrderPersistence commerceOrderPersistence;
	@BeanReference(type = CommerceOrderFinder.class)
	protected CommerceOrderFinder commerceOrderFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderItemLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderItemLocalService commerceOrderItemLocalService;
	@BeanReference(type = CommerceOrderItemPersistence.class)
	protected CommerceOrderItemPersistence commerceOrderItemPersistence;
	@BeanReference(type = CommerceOrderItemFinder.class)
	protected CommerceOrderItemFinder commerceOrderItemFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderNoteLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderNoteLocalService commerceOrderNoteLocalService;
	@BeanReference(type = CommerceOrderNotePersistence.class)
	protected CommerceOrderNotePersistence commerceOrderNotePersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderPaymentLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderPaymentLocalService commerceOrderPaymentLocalService;
	@BeanReference(type = CommerceOrderPaymentPersistence.class)
	protected CommerceOrderPaymentPersistence commerceOrderPaymentPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommercePaymentMethodLocalService.class)
	protected com.liferay.commerce.service.CommercePaymentMethodLocalService commercePaymentMethodLocalService;
	@BeanReference(type = CommercePaymentMethodPersistence.class)
	protected CommercePaymentMethodPersistence commercePaymentMethodPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceRegionLocalService.class)
	protected com.liferay.commerce.service.CommerceRegionLocalService commerceRegionLocalService;
	@BeanReference(type = CommerceRegionPersistence.class)
	protected CommerceRegionPersistence commerceRegionPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceShipmentLocalService.class)
	protected com.liferay.commerce.service.CommerceShipmentLocalService commerceShipmentLocalService;
	@BeanReference(type = CommerceShipmentPersistence.class)
	protected CommerceShipmentPersistence commerceShipmentPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceShipmentItemLocalService.class)
	protected com.liferay.commerce.service.CommerceShipmentItemLocalService commerceShipmentItemLocalService;
	@BeanReference(type = CommerceShipmentItemPersistence.class)
	protected CommerceShipmentItemPersistence commerceShipmentItemPersistence;
	@BeanReference(type = CommerceShipmentItemFinder.class)
	protected CommerceShipmentItemFinder commerceShipmentItemFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceShippingMethodLocalService.class)
	protected com.liferay.commerce.service.CommerceShippingMethodLocalService commerceShippingMethodLocalService;
	@BeanReference(type = CommerceShippingMethodPersistence.class)
	protected CommerceShippingMethodPersistence commerceShippingMethodPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceSubscriptionCycleEntryLocalService.class)
	protected com.liferay.commerce.service.CommerceSubscriptionCycleEntryLocalService commerceSubscriptionCycleEntryLocalService;
	@BeanReference(type = CommerceSubscriptionCycleEntryPersistence.class)
	protected CommerceSubscriptionCycleEntryPersistence commerceSubscriptionCycleEntryPersistence;
	@BeanReference(type = CommerceSubscriptionEntryLocalService.class)
	protected CommerceSubscriptionEntryLocalService commerceSubscriptionEntryLocalService;
	@BeanReference(type = CommerceSubscriptionEntryPersistence.class)
	protected CommerceSubscriptionEntryPersistence commerceSubscriptionEntryPersistence;
	@BeanReference(type = CommerceSubscriptionEntryFinder.class)
	protected CommerceSubscriptionEntryFinder commerceSubscriptionEntryFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceWarehouseLocalService.class)
	protected com.liferay.commerce.service.CommerceWarehouseLocalService commerceWarehouseLocalService;
	@BeanReference(type = CommerceWarehousePersistence.class)
	protected CommerceWarehousePersistence commerceWarehousePersistence;
	@BeanReference(type = CommerceWarehouseFinder.class)
	protected CommerceWarehouseFinder commerceWarehouseFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceWarehouseItemLocalService.class)
	protected com.liferay.commerce.service.CommerceWarehouseItemLocalService commerceWarehouseItemLocalService;
	@BeanReference(type = CommerceWarehouseItemPersistence.class)
	protected CommerceWarehouseItemPersistence commerceWarehouseItemPersistence;
	@BeanReference(type = CommerceWarehouseItemFinder.class)
	protected CommerceWarehouseItemFinder commerceWarehouseItemFinder;
	@BeanReference(type = com.liferay.commerce.service.CPDAvailabilityEstimateLocalService.class)
	protected com.liferay.commerce.service.CPDAvailabilityEstimateLocalService cpdAvailabilityEstimateLocalService;
	@BeanReference(type = CPDAvailabilityEstimatePersistence.class)
	protected CPDAvailabilityEstimatePersistence cpdAvailabilityEstimatePersistence;
	@BeanReference(type = com.liferay.commerce.service.CPDefinitionInventoryLocalService.class)
	protected com.liferay.commerce.service.CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService;
	@BeanReference(type = CPDefinitionInventoryPersistence.class)
	protected CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}