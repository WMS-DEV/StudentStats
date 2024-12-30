import * as S from "./styles";

const FilterIcon = () => (
    <S.FilterIcon viewBox="0 0 24 24">
        <path 
            d="m14 24a1 1 0 0 1 -.6-.2l-4-3a1 1 0 0 1 -.4-.8v-5.62l-7.016-7.893a3.9 3.9 0 0 1 2.916-6.487h14.2a3.9 3.9 0 0 1 2.913 6.488l-7.013 7.892v8.62a1 1 0 0 1 -1 1z"
        />
    </S.FilterIcon>
)

export const CategoryFilter = ({ onClick }) => (
    <S.CategoryFilterButton onClick={onClick}>
        <FilterIcon/>
    </S.CategoryFilterButton>
)
