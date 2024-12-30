import styled from "styled-components";

export const FilterType = styled.div`
height: auto;
margin-block-top: 5px;
padding-block: 5px;

:last-child {
    border-top: 2px solid ${({ theme }) => theme.colorMode.header.borderColor};
}
`;

export const FilterTitle = styled.div`
    line-height: 1.2rem;
    height: auto;
    font-size: 0.85rem;
    color: gray;
`;

export const FilterTitleLarge = styled.div`
    line-height: 1.2rem;
    height: auto;
`;

export const FilterSizes = styled.div`
    height: auto;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
`;

export const CheckboxLabel = styled.div`
    height: auto;
    padding: 5px;
    display: grid;
    grid-template-columns: 1em auto;
`;

export const Label = styled.label`
    height: auto;
    text-transform: capitalize;
    cursor: pointer;
    user-select: none;
    padding-left: 0.5rem;
`;

export const Checkbox = styled.input`
    height: auto;
    border: 2px solid ${({ theme }) => theme.colorMode.fontColor};
    border-radius: 3px;
    -webkit-appearance: none;
    appearance: none;
    margin: 0;
    transition: 0.2s;
    cursor: pointer;

    &:checked {
        background-color: ${({ theme }) => theme.colorMode.fontColor};
    }

    ${FilterType}:not(:has(:checked)) & {
        background-color: gray;
        border-color: gray;
    }
`;