import React from 'react';
import * as S1 from "../styles";
import * as S2 from "./styles";
import { Popover } from '../Popover';
import { useAuth } from '../../../../../../AuthorizationProvider';
import { useTranslation } from 'react-i18next';

const S = {
    ...S1,
    ...S2
};

const LogoutButton = () => {
    const { onLogout } = useAuth();
    return (
        <S.LogoutButton onClick={onLogout}>
            <S.LogoutIcon viewBox="0 -960 960 960">
                <path 
                    d="M180-120q-24 0-42-18t-18-42v-600q0-24 18-42t42-18h291v60H180v600h291v60H180Zm486-185-43-43 102-102H375v-60h348L621-612l43-43 176 176-174 174Z"
                />
            </S.LogoutIcon>
        </S.LogoutButton>
    )
}

const DataRow = ({title1, value1, title2, value2}) => {
    return (
    <S.ProfileDataRow>
        {title1 && value1 &&
        <S.ProfileItem style={{marginRight: '1rem'}}>
            <S.TextLineTitle>{ title1 }</S.TextLineTitle>
            <S.TextLineValue>{ value1 }</S.TextLineValue>
        </S.ProfileItem>
        }
        {title2 && value2 &&
        <S.ProfileItem>
            <S.TextLineTitle>{ title2 }</S.TextLineTitle>
            <S.TextLineValue>{ value2 }</S.TextLineValue>
        </S.ProfileItem>
        }
    </S.ProfileDataRow>
    );
}

export const ProfilePopover = ({ setShowPopover, isPopoverMounted, switchVisibility, menuRef }) => {
    const { t } = useTranslation();

    const { userData } = useAuth();

    const personData = userData?.personalData;

    return (
        <Popover setShowPopover={setShowPopover} isPopoverMounted={isPopoverMounted} switchVisibility={switchVisibility} menuRef={menuRef}>
            <S.ProfileBanner>
                    <S.ProfileBannerAvatar img={personData?.photoUrl ? personData?.photoUrl : "https://apps.usos.pwr.edu.pl/res/up/200x250/blank-male-4.jpg"}></S.ProfileBannerAvatar>
                    <S.NameIndexDiv>
                        <S.ProfileBannerName>{personData?.firstName} {personData?.lastName}</S.ProfileBannerName>
                        <S.ProfileBannerSubtext>{ t('profileIndex') } {personData?.indexNumber}</S.ProfileBannerSubtext>
                        <S.ProfileBannerSubtext>{personData?.universityName}</S.ProfileBannerSubtext>
                    </S.NameIndexDiv>
                    <LogoutButton/>
            </S.ProfileBanner>
            <DataRow title1={t('profileMajor')} value1={personData?.currentMajor} title2={t('profileStudiesStage')} value2={personData?.currentStageOfStudies} />
            <DataRow title1={t('profileStudentStatus')} value1={personData?.studentStatus === 'ACTIVE_STUDENT' ? t('profileActive') : t('profileInactive')} title2={t('profileStudiesType')} value2={personData?.studiesType}/>
            <DataRow title1={t('profileFaculty')} value1={personData?.currentFaculty} />
        </Popover>
    );
}