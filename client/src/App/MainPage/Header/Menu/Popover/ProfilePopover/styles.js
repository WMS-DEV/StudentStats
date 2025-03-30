import styled from "styled-components";

const AVATAR_SIZE = '4rem';

export const ProfileBanner = styled.div`
    border-bottom: 2px solid ${({ theme }) => theme.colorMode.header.borderColor};
    display: flex;
    justify-content: space-between;
    gap: 0.5rem;
    align-items: center;
    padding-bottom: 0.5rem;
    margin-bottom: 5px;
    height: auto;
`

export const NameIndexDiv = styled.div`
    display: flex;
    flex-direction: column;
    height: auto;
`

export const ProfileBannerAvatar = styled.div`
    background-image: url(${({ img }) => img});
    background-size: ${AVATAR_SIZE};
    // border: 1px solid #000;
    box-shadow: 8px 8px 5px rgba(0, 0, 0, 0.1);
    border-radius: 30%;
    width: ${AVATAR_SIZE};
    height: ${AVATAR_SIZE};
    flex: none;
    padding-right: 0.5rem;
`

export const ProfileBannerLabel = styled.div`
    font-size: 1.3rem;
    height: 100%;
    text-align: right;
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
`

export const ProfileBannerName = styled.div`
    line-height: 1.5rem;
    height: auto;
`

export const ProfileBannerSubtext = styled.div`
    height: auto;
    color: #999;
    margin-top: 0.5rem;
`

export const ProfileContent = styled.div`
    padding: 1rem .5rem;
    height: auto;
    display: grid;
    row-gap: 1rem;
    grid-template-columns: 1fr 2fr;
    align-items: end;
`

export const ProfileContentItemDescription = styled.div`
    font-size: .7rem;
    color: #999;
    margin-right: 1rem;
    grid-column: 1;
    height: auto;
`

export const ProfileContentItemValue = styled.div`
    grid-column: 2;
    height: auto;
`

export const ProfileFooter = styled.div`
    margin-top: auto;
    height: 2rem;
    display: flex;
    justify-content: flex-end;
`

export const ProfileDataRow = styled.div`
    padding: 0 .5rem;
    height: auto;
    display: flex;
    justify-content: space-between;
`

export const ProfileItem = styled.div`
    padding: 5px 0;
    height: auto;
`

export const TextLineTitle = styled.div`
    line-height: 1.2rem;
    height: auto;
    font-size: 0.85rem;
    color: gray;
`

export const TextLineValue = styled.div`
    line-height: 1.2rem;
    height: auto;
    text-transform: capitalize;
`
