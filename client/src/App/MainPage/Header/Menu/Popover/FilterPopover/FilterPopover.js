import React, { useRef } from 'react';
import * as S1 from "../styles";
import * as S2 from "./styles";
import { useContext } from 'react';
import { useTranslation } from "react-i18next";
import { Popover } from '../Popover';
import { FiltersContext } from '../../../../FiltersContext'; 
import { useAuth } from '../../../../../../AuthorizationProvider';

const S = {
    ...S1,
    ...S2
};

const translationSizes = {
    en: { 'small': 'small', 'medium': 'medium', 'big': 'big' },
    pl: { 'małe': 'small', 'średnie': 'medium', 'duże': 'big' }
}

const CheckboxLabel = ({ filterKey, filterText, isChecked, updateTilesFilter }) => {
    // const cleanText = tranlationEnToPl[filterText] || filterText;
    const cleanText = filterText;

    return (
        <S.CheckboxLabel>
            <S.Checkbox type='checkbox' id={ `filters-${filterKey}` } onChange={updateTilesFilter} defaultChecked={ isChecked }/>
            <S.Label htmlFor={ `filters-${filterKey}` }>{ cleanText }</S.Label>
        </S.CheckboxLabel>
    );
}

const generateCheckboxes = (dict, filterType, tilesFilter, setTilesFilter) => {

    const updateTilesFilter = (event) => {
        const filterKey = event.target.id.split('-')[1];
        const index = tilesFilter[filterType].indexOf(filterKey);
        if(index === -1) tilesFilter[filterType].push(filterKey);
        else tilesFilter[filterType].splice(index, 1);

        // category Filters are stored as ID in localStorage. ID is order in original json
        const tilesFilterCopy = JSON.parse(JSON.stringify(tilesFilter));
        
        setTilesFilter(tilesFilterCopy);
    }

    return Object.entries(dict).map(keyFilter => {
        const isChecked = tilesFilter[filterType].includes(keyFilter[1].toString());
        return <CheckboxLabel key={ keyFilter[1] } filterKey={ keyFilter[1] } filterText={ keyFilter[0] } isChecked={ isChecked } updateTilesFilter={updateTilesFilter}/>
    })
}

export const FilterPopover = ({ setShowPopover, isPopoverMounted, switchVisibility, menuRef }) => {
   
    const { tilesFilter, setTilesFilter } = useContext(FiltersContext);
    const { t, i18n } = useTranslation();

    const { userData } = useAuth();

    // sizes checkboxes
    const sizesCheckboxes = useRef([]);
    const sizes = translationSizes[i18n.language];

    sizesCheckboxes.current = generateCheckboxes(sizes, 'sizes', tilesFilter, setTilesFilter);

    // categories checkboxes
    const categoriesCheckboxes = useRef([]);
    const categories = {};
    let catCounter = 0;

    const tiles = userData ? userData.content : [];
    tiles.forEach(tile => {
        if(tile !== null && !categories.hasOwnProperty(tile.category) && tile.category !== 'BRANDING') categories[tile.category] = catCounter++;
    });
    
    categoriesCheckboxes.current = generateCheckboxes(categories, 'categories', tilesFilter, setTilesFilter);

    return (
        <Popover setShowPopover={setShowPopover} isPopoverMounted={isPopoverMounted} switchVisibility={switchVisibility} menuRef={menuRef}>
            <S.FilterTitleLarge>{ t('filterTitle') } </S.FilterTitleLarge>
            <S.FilterType>
                <S.FilterTitle>{ t('filterSize') }</S.FilterTitle>
                <S.FilterSizes>
                    { sizesCheckboxes.current }
                </S.FilterSizes>
            </S.FilterType>
            <S.FilterType>
                <S.FilterTitle>{ t('filterCategory') }</S.FilterTitle>
                { categoriesCheckboxes.current }
            </S.FilterType>

        </Popover>
    );
}