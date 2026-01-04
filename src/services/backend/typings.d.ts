declare namespace API {
  type adminApproveAssetApplicationParams = {
    id: number;
    result: string;
    comment: string;
    approverId: number;
  };

  type adminApproveLeaveApplicationParams = {
    id: number;
    result: string;
    comment: string;
    approverId: number;
  };

  type Announcement = {
    id?: number;
    title: string;
    content: string;
    type: string;
    validFrom: string;
    validTo: string;
    publishScope: string;
    departmentIds?: string;
    employeeIds?: string;
    attachments?: string;
    status: string;
    publisherId: number;
    publishTime?: string;
    version: number;
    createTime?: string;
    updateTime?: string;
  };

  type AnnouncementReadRecord = {
    id?: number;
    announcementId?: number;
    employeeId?: number;
    readTime?: string;
    isConfirmed?: number;
    confirmTime?: string;
    createTime?: string;
  };

  type ApprovalRecord = {
    id?: number;
    applicationId?: number;
    applicationType?: string;
    approverId?: number;
    approverRole?: string;
    result?: string;
    comment?: string;
    approvalTime?: string;
    createTime?: string;
  };

  type AssetApplicationDto = {
    id?: number;
    employeeId: number;
    assetId: number;
    requestQuantity: number;
    purpose: string;
    status?: string;
    managerComment?: string;
    adminComment?: string;
    createTime?: string;
    updateTime?: string;
  };

  type AssetCatalogDto = {
    id?: number;
    type: string;
    name: string;
    description?: string;
    stockQuantity: number;
    unit: string;
    price: number;
    budgetLimit?: number;
    status: string;
    createTime?: string;
    updateTime?: string;
  };

  type AssetDistribution = {
    id?: number;
    applicationId?: number;
    assetId?: number;
    distributionQuantity?: number;
    employeeId?: number;
    distributionTime?: string;
    receiveStatus?: string;
    employeeFeedback?: string;
    adminProcessResult?: string;
    createTime?: string;
    updateTime?: string;
  };

  type AttendanceRecord = {
    id?: number;
    employeeId?: number;
    recordDate?: string;
    status?: string;
    checkInTime?: string;
    checkOutTime?: string;
    leaveHours?: number;
    overtimeHours?: number;
    description?: string;
    createTime?: string;
    updateTime?: string;
  };

  type auditSalaryDetailParams = {
    id: number;
    result: string;
    auditorId: number;
  };

  type confirmAnnouncementReadParams = {
    announcementId: number;
    employeeId: number;
  };

  type confirmReceiveAssetParams = {
    distributionId: number;
  };

  type deleteAnnouncementParams = {
    id: number;
  };

  type deleteAssetCatalogParams = {
    id: number;
  };

  type deleteAttendanceRecordParams = {
    id: number;
  };

  type deleteDepartmentParams = {
    id: number;
  };

  type deleteEmployeeParams = {
    id: number;
  };

  type deleteFinancialExpenseParams = {
    id: number;
  };

  type deletePerformanceRecordParams = {
    id: number;
  };

  type deletePositionParams = {
    id: number;
  };

  type deleteSalaryDetailParams = {
    id: number;
  };

  type deleteSalaryDisputeParams = {
    id: number;
  };

  type Department = {
    id?: number;
    code: string;
    name: string;
    parentId?: number;
    managerId?: number;
    description?: string;
    createTime?: string;
    updateTime?: string;
  };

  type Employee = {
    id?: number;
    username: string;
    password: string;
    name: string;
    email?: string;
    phone?: string;
    departmentId: number;
    positionId: number;
    createTime?: string;
  };

  type EmployeeDto = {
    id?: number;
    username?: string;
    password?: string;
    name?: string;
    email?: string;
    phone?: string;
    department?: string;
    position?: string;
    createTime?: string;
  };

  type FinancialExpense = {
    id?: number;
    type?: string;
    amount?: number;
    expenseDate?: string;
    purpose?: string;
    relatedId?: number;
    relatedType?: string;
    paymentMethod?: string;
    remark?: string;
    creatorId?: number;
    createTime?: string;
    updateTime?: string;
  };

  type getAllAnnouncementsParams = {
    page?: number;
    size?: number;
  };

  type getAllAssetApplicationsParams = {
    page?: number;
    size?: number;
  };

  type getAllAssetCatalogsParams = {
    page?: number;
    size?: number;
  };

  type getAllAttendanceRecordsParams = {
    page?: number;
    size?: number;
  };

  type getAllDepartmentsParams = {
    page?: number;
    size?: number;
  };

  type getAllEmployeesParams = {
    page?: number;
    size?: number;
  };

  type getAllFinancialExpensesParams = {
    page?: number;
    size?: number;
  };

  type getAllLeaveApplicationsParams = {
    page?: number;
    size?: number;
  };

  type getAllPerformanceRecordsParams = {
    page?: number;
    size?: number;
  };

  type getAllPositionsParams = {
    page?: number;
    size?: number;
  };

  type getAllSalaryDetailsParams = {
    page?: number;
    size?: number;
  };

  type getAllSalaryDisputesParams = {
    page?: number;
    size?: number;
  };

  type getAnnouncementByIdParams = {
    id: number;
  };

  type getAnnouncementReadRecordsParams = {
    announcementId: number;
  };

  type getApprovalRecords1Params = {
    id: number;
  };

  type getApprovalRecordsParams = {
    id: number;
  };

  type getAssetApplicationByIdParams = {
    id: number;
  };

  type getAssetApplicationsByEmployeeIdParams = {
    employeeId: number;
  };

  type getAssetCatalogByIdParams = {
    id: number;
  };

  type getAssetCatalogsByTypeParams = {
    type: string;
  };

  type getAssetDistributionsParams = {
    id: number;
  };

  type getAttendanceRecordByIdParams = {
    id: number;
  };

  type getAttendanceRecordsByEmployeeIdParams = {
    employeeId: number;
  };

  type getChildDepartmentsParams = {
    parentId: number;
  };

  type getDepartmentByIdParams = {
    id: number;
  };

  type getEmployeeAnnouncementReadRecordsParams = {
    employeeId: number;
  };

  type getEmployeeByIdParams = {
    id: number;
  };

  type getEmployeesByDepartmentIdParams = {
    departmentId: number;
  };

  type getFinancialExpenseByIdParams = {
    id: number;
  };

  type getFinancialExpensesByCreatorIdParams = {
    creatorId: number;
  };

  type getFinancialExpensesByDateRangeParams = {
    startDate: string;
    endDate: string;
  };

  type getFinancialExpensesByTypeParams = {
    type: string;
  };

  type getLeaveApplicationByIdParams = {
    id: number;
  };

  type getLeaveApplicationsByEmployeeIdParams = {
    employeeId: number;
  };

  type getPerformanceRecordByIdParams = {
    id: number;
  };

  type getPerformanceRecordsByEmployeeIdParams = {
    employeeId: number;
  };

  type getPerformanceRecordsByPeriodParams = {
    performancePeriod: string;
  };

  type getPositionByIdParams = {
    id: number;
  };

  type getPositionsByDepartmentIdParams = {
    departmentId: number;
  };

  type getSalaryDetailByIdParams = {
    id: number;
  };

  type getSalaryDetailsByEmployeeIdParams = {
    employeeId: number;
  };

  type getSalaryDetailsByPeriodParams = {
    salaryPeriod: string;
  };

  type getSalaryDisputeByIdParams = {
    id: number;
  };

  type getSalaryDisputesByEmployeeIdParams = {
    employeeId: number;
  };

  type getSalaryDisputesBySalaryDetailIdParams = {
    salaryDetailId: number;
  };

  type getSalaryDisputesByStatusParams = {
    status: string;
  };

  type issueSalaryParams = {
    id: number;
  };

  type LeaveApplication = {
    id?: number;
    employeeId: number;
    type: string;
    startTime: string;
    endTime: string;
    reason: string;
    attachment?: string;
    status: string;
    managerComment?: string;
    adminComment?: string;
    createTime?: string;
    updateTime?: string;
  };

  type LoginDto = {
    username: string;
    password: string;
  };

  type managerApproveAssetApplicationParams = {
    id: number;
    result: string;
    comment: string;
    approverId: number;
  };

  type managerApproveLeaveApplicationParams = {
    id: number;
    result: string;
    comment: string;
    approverId: number;
  };

  type PageableObject = {
    offset?: number;
    sort?: SortObject;
    pageSize?: number;
    pageNumber?: number;
    unpaged?: boolean;
    paged?: boolean;
  };

  type PageAnnouncement = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: Announcement[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageAssetApplicationDto = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: AssetApplicationDto[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageAssetCatalogDto = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: AssetCatalogDto[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageAttendanceRecord = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: AttendanceRecord[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageDepartment = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: Department[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageEmployeeDto = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: EmployeeDto[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageFinancialExpense = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: FinancialExpense[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageLeaveApplication = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: LeaveApplication[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PagePerformanceRecordDto = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: PerformanceRecordDto[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PagePosition = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: Position[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageSalaryDetail = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: SalaryDetail[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PageSalaryDispute = {
    totalElements?: number;
    totalPages?: number;
    size?: number;
    content?: SalaryDispute[];
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
  };

  type PerformanceRecordDto = {
    id?: number;
    employeeId?: number;
    performancePeriod?: string;
    score?: number;
    grade?: string;
    comment?: string;
    appraiserId?: number;
    createTime?: string;
    updateTime?: string;
  };

  type Position = {
    id?: number;
    name: string;
    departmentId: number;
    description?: string;
    createTime?: string;
  };

  type processAssetObjectionParams = {
    distributionId: number;
    processResult: string;
  };

  type publishAnnouncementParams = {
    id: number;
    publisherId: number;
  };

  type raiseAssetObjectionParams = {
    distributionId: number;
    feedback: string;
  };

  type recordAnnouncementReadParams = {
    announcementId: number;
    employeeId: number;
  };

  type respondToSalaryDisputeParams = {
    id: number;
    hrResponse: string;
  };

  type ResponseDtoAnnouncement = {
    code?: number;
    message?: string;
    data?: Announcement;
  };

  type ResponseDtoAnnouncementReadRecord = {
    code?: number;
    message?: string;
    data?: AnnouncementReadRecord;
  };

  type ResponseDtoAssetApplicationDto = {
    code?: number;
    message?: string;
    data?: AssetApplicationDto;
  };

  type ResponseDtoAssetCatalogDto = {
    code?: number;
    message?: string;
    data?: AssetCatalogDto;
  };

  type ResponseDtoAssetDistribution = {
    code?: number;
    message?: string;
    data?: AssetDistribution;
  };

  type ResponseDtoAttendanceRecord = {
    code?: number;
    message?: string;
    data?: AttendanceRecord;
  };

  type ResponseDtoDepartment = {
    code?: number;
    message?: string;
    data?: Department;
  };

  type ResponseDtoEmployeeDto = {
    code?: number;
    message?: string;
    data?: EmployeeDto;
  };

  type ResponseDtoFinancialExpense = {
    code?: number;
    message?: string;
    data?: FinancialExpense;
  };

  type ResponseDtoLeaveApplication = {
    code?: number;
    message?: string;
    data?: LeaveApplication;
  };

  type ResponseDtoListAnnouncement = {
    code?: number;
    message?: string;
    data?: Announcement[];
  };

  type ResponseDtoListAnnouncementReadRecord = {
    code?: number;
    message?: string;
    data?: AnnouncementReadRecord[];
  };

  type ResponseDtoListApprovalRecord = {
    code?: number;
    message?: string;
    data?: ApprovalRecord[];
  };

  type ResponseDtoListAssetApplicationDto = {
    code?: number;
    message?: string;
    data?: AssetApplicationDto[];
  };

  type ResponseDtoListAssetCatalogDto = {
    code?: number;
    message?: string;
    data?: AssetCatalogDto[];
  };

  type ResponseDtoListAssetDistribution = {
    code?: number;
    message?: string;
    data?: AssetDistribution[];
  };

  type ResponseDtoListAttendanceRecord = {
    code?: number;
    message?: string;
    data?: AttendanceRecord[];
  };

  type ResponseDtoListDepartment = {
    code?: number;
    message?: string;
    data?: Department[];
  };

  type ResponseDtoListEmployeeDto = {
    code?: number;
    message?: string;
    data?: EmployeeDto[];
  };

  type ResponseDtoListFinancialExpense = {
    code?: number;
    message?: string;
    data?: FinancialExpense[];
  };

  type ResponseDtoListLeaveApplication = {
    code?: number;
    message?: string;
    data?: LeaveApplication[];
  };

  type ResponseDtoListPerformanceRecordDto = {
    code?: number;
    message?: string;
    data?: PerformanceRecordDto[];
  };

  type ResponseDtoListPosition = {
    code?: number;
    message?: string;
    data?: Position[];
  };

  type ResponseDtoListSalaryDetail = {
    code?: number;
    message?: string;
    data?: SalaryDetail[];
  };

  type ResponseDtoListSalaryDispute = {
    code?: number;
    message?: string;
    data?: SalaryDispute[];
  };

  type ResponseDtoObject = {
    code?: number;
    message?: string;
    data?: Record<string, any>;
  };

  type ResponseDtoPageAnnouncement = {
    code?: number;
    message?: string;
    data?: PageAnnouncement;
  };

  type ResponseDtoPageAssetApplicationDto = {
    code?: number;
    message?: string;
    data?: PageAssetApplicationDto;
  };

  type ResponseDtoPageAssetCatalogDto = {
    code?: number;
    message?: string;
    data?: PageAssetCatalogDto;
  };

  type ResponseDtoPageAttendanceRecord = {
    code?: number;
    message?: string;
    data?: PageAttendanceRecord;
  };

  type ResponseDtoPageDepartment = {
    code?: number;
    message?: string;
    data?: PageDepartment;
  };

  type ResponseDtoPageEmployeeDto = {
    code?: number;
    message?: string;
    data?: PageEmployeeDto;
  };

  type ResponseDtoPageFinancialExpense = {
    code?: number;
    message?: string;
    data?: PageFinancialExpense;
  };

  type ResponseDtoPageLeaveApplication = {
    code?: number;
    message?: string;
    data?: PageLeaveApplication;
  };

  type ResponseDtoPagePerformanceRecordDto = {
    code?: number;
    message?: string;
    data?: PagePerformanceRecordDto;
  };

  type ResponseDtoPagePosition = {
    code?: number;
    message?: string;
    data?: PagePosition;
  };

  type ResponseDtoPageSalaryDetail = {
    code?: number;
    message?: string;
    data?: PageSalaryDetail;
  };

  type ResponseDtoPageSalaryDispute = {
    code?: number;
    message?: string;
    data?: PageSalaryDispute;
  };

  type ResponseDtoPerformanceRecordDto = {
    code?: number;
    message?: string;
    data?: PerformanceRecordDto;
  };

  type ResponseDtoPosition = {
    code?: number;
    message?: string;
    data?: Position;
  };

  type ResponseDtoSalaryDetail = {
    code?: number;
    message?: string;
    data?: SalaryDetail;
  };

  type ResponseDtoSalaryDispute = {
    code?: number;
    message?: string;
    data?: SalaryDispute;
  };

  type SalaryDetail = {
    id?: number;
    employeeId: number;
    salaryPeriod: string;
    basicSalary: number;
    performanceBonus?: number;
    allowance?: number;
    overtimePay?: number;
    socialSecurityDeduction?: number;
    housingFundDeduction?: number;
    taxDeduction?: number;
    otherDeductions?: number;
    netSalary: number;
    status: string;
    auditorId?: number;
    auditTime?: string;
    paymentTime?: string;
    createTime?: string;
    updateTime?: string;
  };

  type SalaryDispute = {
    id?: number;
    employeeId?: number;
    salaryDetailId?: number;
    disputeContent?: string;
    disputeTime?: string;
    hrResponse?: string;
    hrResponseTime?: string;
    status?: string;
    createTime?: string;
    updateTime?: string;
  };

  type SortObject = {
    empty?: boolean;
    sorted?: boolean;
    unsorted?: boolean;
  };

  type transferEmployeesParams = {
    fromDepartmentId: number;
    toDepartmentId: number;
  };

  type updateAnnouncementParams = {
    id: number;
  };

  type updateAssetCatalogParams = {
    id: number;
  };

  type updateAttendanceRecordParams = {
    id: number;
  };

  type updateDepartmentParams = {
    id: number;
  };

  type updateEmployeeParams = {
    id: number;
  };

  type updateEmployeeProfileParams = {
    id: number;
  };

  type updateFinancialExpenseParams = {
    id: number;
  };

  type updatePerformanceRecordParams = {
    id: number;
  };

  type updatePositionParams = {
    id: number;
  };

  type updateSalaryDetailParams = {
    id: number;
  };
}
