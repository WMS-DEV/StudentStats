import * as S from "./styles";
import { useTranslation } from "react-i18next";
import { useAuth } from "../../../../../AuthorizationProvider";

const UserIcon = () => (
    <S.UserIcon viewBox="0 0 32 32">
        <g xmlns="http://www.w3.org/2000/svg" id="surface1">
            <path d="M 16 16 C 19.683594 16 22.667969 13.015625 22.667969 9.332031 C 22.667969 5.652344 19.683594 2.667969 16 2.667969 C 12.316406 2.667969 9.332031 5.652344 9.332031 9.332031 C 9.332031 13.015625 12.316406 16 16 16 Z M 16 16 "/>
            <path d="M 16 19.332031 C 9.320312 19.332031 3.878906 23.8125 3.878906 29.332031 C 3.878906 29.707031 4.171875 30 4.546875 30 L 27.453125 30 C 27.828125 30 28.121094 29.707031 28.121094 29.332031 C 28.121094 23.8125 22.679688 19.332031 16 19.332031 Z M 16 19.332031 "/>
        </g>
    </S.UserIcon>
)

export const PersonalData = ({ onClick }) => {
    const { t } = useTranslation();
    const { isDataLoading } = useAuth();
    return (
    <S.PersonalDataButton onClick={onClick} data-desc={t('menuTipPersonalData')} disabled={isDataLoading}>
        <UserIcon/>
    </S.PersonalDataButton>
    );
};