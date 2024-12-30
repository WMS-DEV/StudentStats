import React, { useState, createContext } from 'react';

export const FiltersContext = createContext();

export const FiltersProvider = ({ children }) => {
    const [tilesFilter, setTilesFilter] = useState({sizes: [], categories: []});

    return (
        <FiltersContext.Provider providerInitState={{sizes: [], categories: []}} value={{ tilesFilter, setTilesFilter }}>
            {children}
        </FiltersContext.Provider>
    );
};