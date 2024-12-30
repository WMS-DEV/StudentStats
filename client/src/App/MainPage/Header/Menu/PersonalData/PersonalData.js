import * as S from "./styles";

const UserIcon = () => (
    <S.UserIcon viewBox="0 0 24 24">
        <path 
            d="M12,12A6,6,0,1,0,6,6,6.006,6.006,0,0,0,12,12ZM12,2A4,4,0,1,1,8,6,4,4,0,0,1,12,2Z"
        />
        <path 
            d="M12,14a9.01,9.01,0,0,0-9,9,1,1,0,0,0,2,0,7,7,0,0,1,14,0,1,1,0,0,0,2,0A9.01,9.01,0,0,0,12,14Z"
        />
    </S.UserIcon>
)

export const PersonalData = ({ onClick }) => (
    <S.PersonalDataButton onClick={onClick}>
        <UserIcon/>
    </S.PersonalDataButton>
)