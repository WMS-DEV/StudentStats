import React from 'react';
import * as S from './styles';
import { useState } from 'react';
import { useEffect, useContext, useRef } from 'react';
import { useAuth } from '../../../AuthorizationProvider';
import { useTranslation } from 'react-i18next';
import { Tile } from './styles.js';
import { TileBig } from './TileBig/TileBig.js';
import { TileMedium } from './TileMedium/TileMedium.js';
import { TileSmall } from './TileSmall/TileSmall.js';   
import { TileBranding } from './TileBranding/TileBranding.js'; 

import { FiltersContext } from '../FiltersContext.js';
import { useNavigate } from 'react-router-dom';





//Functions that solve the tiles distribution problem

let numOfSmallTiles;
let numOfMediumTiles;
let numOfBigTiles;
let sFactor, mFactor, bFactor, currentCol, currentRow, bToMRatio, bToSRatio, mToSRatio, initBToMRatio, initBToSRatio, initMToSRatio;
let res = [];
let lastBigIn1stColumn;
let lastBigIsIn3rdColumn;
let columnOfNewBigTile;

const addTile = (type) => {
    if(type !== 'empty')
        res.push(type);
    switch(type){
        case 'small':
            numOfSmallTiles--;
            currentCol += 1;
            if(currentCol > 5){
                currentCol = 1;
                currentRow += 1;
            }
        break;
        case 'medium':
            numOfMediumTiles--;
            currentCol += 2;
            if(currentCol > 5){
                currentCol = 1;
                currentRow += 1;
            }
        break;
        case 'big':
            numOfBigTiles--;
            columnOfNewBigTile = (columnOfNewBigTile === 1) ? 3 : 1;
            if(currentCol === 1)
                lastBigIn1stColumn = true;
            if(currentCol === 3)
                lastBigIsIn3rdColumn = true;
            currentCol += 3;
            if(currentCol > 5){
                currentCol = 1;
                currentRow += 1;
            }
        break;
        case 'empty':
            currentCol++;
            if(currentCol > 5){
                if(lastBigIsIn3rdColumn)
                    lastBigIsIn3rdColumn = false;
                currentCol -= 5;
                currentRow += 1;
            }
            if(currentCol > 3){
                if(lastBigIn1stColumn)
                    lastBigIn1stColumn = false;
            }
        break;
        default: break;
    }
}

const computeTilesOrder = () => {
    res = [];
    currentCol = 1;
    lastBigIn1stColumn = false;
    lastBigIsIn3rdColumn = false;
    columnOfNewBigTile = 1;
    sFactor = numOfSmallTiles;
    mFactor = numOfMediumTiles*2;
    bFactor = numOfBigTiles*6;
    initBToMRatio = bFactor/mFactor;
    initBToSRatio = bFactor/sFactor;
    initMToSRatio = mFactor/sFactor;

    while(numOfSmallTiles > 0 || numOfMediumTiles > 0 || numOfBigTiles > 0){
        sFactor = numOfSmallTiles;
        mFactor = numOfMediumTiles*2;
        bFactor = numOfBigTiles*6;
        bToMRatio = bFactor/mFactor;
        bToSRatio = bFactor/sFactor;
        mToSRatio = mFactor/sFactor;

        switch (currentCol){
            case 1:
                if(lastBigIn1stColumn)
                    addTile('empty');
                else if(bFactor > 0 && bToMRatio >= initBToMRatio && bToSRatio >= initBToSRatio && !lastBigIsIn3rdColumn && columnOfNewBigTile === 1)
                    addTile('big');
                else if(mFactor > 0 && mToSRatio >= initMToSRatio)
                    addTile('medium');
                else if(sFactor)
                    addTile('small');
                else if(bFactor)
                    addTile('big');
                break;
            case 2:
                if(lastBigIn1stColumn)
                    addTile('empty');
                else if(mFactor > 0 && mToSRatio >= initMToSRatio && bToMRatio <= initBToMRatio && !lastBigIsIn3rdColumn)
                    addTile('medium');
                else if(sFactor > 0)
                    addTile('small');
                else
                    addTile('empty');
                break;
            case 3:
                if(lastBigIn1stColumn || lastBigIsIn3rdColumn)
                    addTile('empty');
                else if(bFactor > 0 && bToMRatio >= initBToMRatio && bToSRatio >= initBToSRatio && columnOfNewBigTile === 3)
                    addTile('big');
                else if(mFactor > 0 && mToSRatio >= initMToSRatio)
                    addTile('medium');
                else if(sFactor)
                    addTile('small');
                break;
            case 4:
                if(lastBigIsIn3rdColumn)
                    addTile('empty');
                else if(mFactor > 0 && mToSRatio >= initMToSRatio)
                    addTile('medium');
                else if(sFactor > 0)
                    addTile('small');
                else
                    addTile('empty');
                break;
            case 5:
                if(lastBigIsIn3rdColumn)
                    addTile('empty');
                else if(sFactor > 0)
                    addTile('small');
                else
                    addTile('empty');
                break;
            default: break;
        }
    }

    return res;
}



// definition of WMS tile

const brandingTilePL = {
    category: 'BRANDING',
    content: {
        title: 'Stworzone z pasją przez',
        subtitle: 'Dołącz do nas!',
        url: 'https://wmsdev.pl/'
    },
    type: 'IMAGE'
}  

const brandingTileEN = {
    category: 'BRANDING',
    content: {
        title: 'Created with passion by',
        subtitle: 'Join us!',
        url: 'https://wmsdev.pl/'
    },
    type: 'IMAGE'
}  

//Functions that assign tiles to data

const SizeMatchingRules = {
    FLAG: 'small',
    TEXT: 'small',
    DOUBLE_TEXT: 'medium',
    CHART: 'big',
    IMAGE: 'small'
}

const filterTilesSize = (tiles, size) => {
    return tiles.filter(x => SizeMatchingRules[x.type] === size);
}

const filterTiles = (tiles, tilesFilter) => {
    // categories stored as IDs in filter
    const categories = {};
    let catCounter = 0;
    tiles.forEach(tile => {
        if(tile !== null && !categories.hasOwnProperty(tile.category)) categories[tile.category] = `${catCounter++}`;
    });

    return tiles.filter(x => {
        const isSizeIncluded = tilesFilter['sizes'].includes(SizeMatchingRules[x.type]) || tilesFilter['sizes'].length === 0;
        const isCategoryIncluded = tilesFilter['categories'].includes(categories[x.category]) || tilesFilter['categories'].length === 0;
        return (isSizeIncluded && isCategoryIncluded) || x.category === 'BRANDING';
    });
} 

const positionTiles = (tiles, tilesOrderCopy) => {
    let tilesDict = {
        small: filterTilesSize(tiles, 'small').reverse(),
        medium: filterTilesSize(tiles, 'medium').reverse(),
        big: filterTilesSize(tiles, 'big').reverse()
    }

    return tilesOrderCopy.map(size => tilesDict[size].pop());
}





export const TilesSection = () => {
    const [tiles, setTiles] = useState([]);
    const tilesOrder = useRef([]);
    const { i18n } = useTranslation();

    const { tilesFilter } = useContext(FiltersContext);
    const { userData } = useAuth();

    useEffect(() => {
        if(!userData) return;
        userData.content.push(i18n.language === 'en' ? brandingTileEN : brandingTilePL);
    }, [userData]);

    useEffect(() => {
        // localStorage.setItem('tiles', JSON.stringify(tilesPack));
        if(!userData) {
            setTiles([]);
            return;
        }
        
        const filteredTiles = filterTiles(userData.content, tilesFilter);

        numOfSmallTiles = filterTilesSize(filteredTiles, 'small').length;
        numOfMediumTiles = filterTilesSize(filteredTiles, 'medium').length;
        numOfBigTiles = filterTilesSize(filteredTiles, 'big').length;

        tilesOrder.current = computeTilesOrder();

        const newTiles = positionTiles(filteredTiles, tilesOrder.current.slice())

        setTiles(newTiles);
    }, [userData, tilesFilter, i18n.language]);

    // const [userData, setUserData] = useState(null);

    if(tiles && tiles.length > 0){
        return(
            <S.TilesSection>
                {tilesOrder.current.map((size, ix) => {return (tiles[ix].category === 'BRANDING') ? <TileBranding data={tiles[ix]} key={ix}/>
                                                    : (size === 'big') ? <TileBig data={tiles[ix]} key={ix}/> 
                                                    : (size === 'medium') ? <TileMedium data={tiles[ix]} key={ix}/> 
                                                    : <TileSmall data={tiles[ix]} key={ix}/>})}
            </S.TilesSection>
        )
    } else {
        // title, subtitle, value1, value2
        const emptyData = {
            content: {
                title: '',
                subtitle: '',
                value: '',
                values: [],
                value1: '',
                value2: ''
            }
        }
        return <S.TilesSection isDataEmpty={true}>
            <TileSmall data={emptyData}></TileSmall>
            <TileMedium data={emptyData}></TileMedium>
            <TileMedium data={emptyData}></TileMedium>
            <TileMedium data={emptyData}></TileMedium>
            <TileSmall data={emptyData}></TileSmall>
            <TileMedium data={emptyData}></TileMedium>
            <TileSmall data={emptyData}></TileSmall>
            <TileMedium data={emptyData}></TileMedium>
            <TileMedium data={emptyData}></TileMedium>
            <TileMedium data={emptyData}></TileMedium>
            <TileSmall data={emptyData}></TileSmall>
            <TileMedium data={emptyData}></TileMedium>
        </S.TilesSection>
    }
}