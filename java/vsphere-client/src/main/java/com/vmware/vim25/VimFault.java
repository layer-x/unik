
package com.vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VimFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VimFault">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}MethodFault">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VimFault")
@XmlSeeAlso({
    NotFound.class,
    StorageDrsStaleHmsCollection.class,
    SsdDiskNotAvailable.class,
    StorageDrsRelocateDisabled.class,
    InvalidName.class,
    InvalidPrivilege.class,
    AlreadyExists.class,
    StorageDrsHmsUnreachable.class,
    CannotDisconnectHostWithFaultToleranceVm.class,
    NetworkDisruptedAndConfigRolledBack.class,
    NotSupportedHostForChecksum.class,
    StorageDrsIolbDisabledInternally.class,
    CannotMoveFaultToleranceVm.class,
    NoSubjectName.class,
    StorageDrsHbrDiskNotMovable.class,
    InvalidLocale.class,
    LicenseEntityNotFound.class,
    MismatchedBundle.class,
    InvalidIpmiLoginInfo.class,
    RecordReplayDisabled.class,
    MissingBmcSupport.class,
    InsufficientStorageIops.class,
    PatchBinariesNotFound.class,
    RebootRequired.class,
    StorageDrsCannotMoveManuallyPlacedSwapFile.class,
    InaccessibleVFlashSource.class,
    StorageDrsUnableToMoveFiles.class,
    StorageDrsCannotMoveTemplate.class,
    NoDiskFound.class,
    InvalidBmcRole.class,
    AnswerFileUpdateFailed.class,
    InvalidIpmiMacAddress.class,
    ExtendedFault.class,
    CannotMoveHostWithFaultToleranceVm.class,
    InvalidLicense.class,
    DrsDisabledOnVm.class,
    WipeDiskFault.class,
    VFlashModuleVersionIncompatible.class,
    RemoveFailed.class,
    LicenseServerUnavailable.class,
    UnrecognizedHost.class,
    ShrinkDiskFault.class,
    LogBundlingFailed.class,
    CannotEnableVmcpForCluster.class,
    StorageDrsDatacentersCannotShareDatastore.class,
    FaultToleranceVmNotDasProtected.class,
    UnsupportedVimApiVersion.class,
    StorageDrsCannotMoveIndependentDisk.class,
    NoCompatibleHost.class,
    GenericDrsFault.class,
    AuthMinimumAdminPermission.class,
    CannotPlaceWithoutPrerequisiteMoves.class,
    PatchNotApplicable.class,
    CannotAccessLocalSource.class,
    HostIncompatibleForRecordReplay.class,
    ResourceNotAvailable.class,
    UserNotFound.class,
    NoConnectedDatastore.class,
    StorageDrsCannotMoveFTVm.class,
    OutOfBounds.class,
    AlreadyUpgraded.class,
    IORMNotSupportedHostOnDatastore.class,
    NoCompatibleDatastore.class,
    Timedout.class,
    NamespaceWriteProtected.class,
    StorageDrsCannotMoveVmInUserFolder.class,
    StorageDrsCannotMoveVmWithMountedCDROM.class,
    FcoeFault.class,
    StorageDrsHmsMoveInProgress.class,
    NoClientCertificate.class,
    ReplicationFault.class,
    ToolsUnavailable.class,
    SwapDatastoreUnset.class,
    ResourceInUse.class,
    ConcurrentAccess.class,
    InvalidFolder.class,
    StorageDrsCannotMoveManuallyPlacedVm.class,
    DasConfigFault.class,
    StorageDrsCannotMoveVmWithNoFilesInLayout.class,
    TooManyConsecutiveOverrides.class,
    HostPowerOpFailed.class,
    NamespaceLimitReached.class,
    StorageDrsCannotMoveSharedDisk.class,
    VAppConfigFault.class,
    NamespaceFull.class,
    InvalidAffinitySettingFault.class,
    HostConfigFault.class,
    PatchMetadataInvalid.class,
    VmMonitorIncompatibleForFaultTolerance.class,
    InvalidState.class,
    SSPIChallenge.class,
    StorageDrsDisabledOnVm.class,
    VmValidateMaxDevice.class,
    EVCConfigFault.class,
    FileFault.class,
    LimitExceeded.class,
    GuestOperationsFault.class,
    VsanFault.class,
    VmFaultToleranceIssue.class,
    IscsiFault.class,
    InvalidEvent.class,
    ProfileUpdateFailed.class,
    DvsFault.class,
    InsufficientResourcesFault.class,
    CustomizationFault.class,
    HostConnectFault.class,
    InvalidLogin.class,
    VmMetadataManagerFault.class,
    VmToolsUpgradeFault.class,
    SnapshotFault.class,
    StorageDrsCannotMoveDiskInMultiWriterMode.class,
    DuplicateName.class,
    TaskInProgress.class,
    MigrationFault.class,
    VmConfigFault.class,
    InvalidDatastore.class,
    HostHasComponentFailure.class,
    OvfFault.class,
    ActiveDirectoryFault.class
})
public class VimFault
    extends MethodFault
{


}
