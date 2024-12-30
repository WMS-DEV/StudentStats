import React, { useState, useEffect, useContext } from 'react';
import * as S from './styles';
import { LineChart } from './LineChart/LineChart';
import { BarChart } from './BarChart/BarChart';
import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';
import { ColorModeContext } from '../../../../App';
import { Mode } from '../../Header/Menu/ColorMode/ColorMode';

Chart.register(CategoryScale);

export const TileBig = ({ data }) => {
    let { subtitle, title, values } = data.content;
    const { colorMode } = useContext(ColorModeContext);

    const getColors = (mode) => {
        return mode === Mode.LIGHT ? ['#44804F', 'black'] : ['#4AE174', '#CEC8BC'];
    };

    const [chartData, setChartData] = useState({
        labels: values.map(x => x.label),
        datasets: [{
            data: values.map(x => x.value),
            backgroundColor: getColors(colorMode)[0],
            borderColor: getColors(colorMode)[1],
            borderWidth: 1,
            pointRadius: 4,
        }]
    });

    useEffect(() => {
        const colors = getColors(colorMode);
        setChartData(currentData => ({
            ...currentData,
            datasets: currentData.datasets.map(dataset => ({
                ...dataset,
                backgroundColor: colors[0],
                borderColor: colors[1],
                pointBackgroundColor: colors[0],
                pointBorderColor: colors[0],
            }))
        }));
    }, [colorMode]);

    return (
        <S.TileBig>
            {(data.content.chartType === 'LINE') ? <LineChart chartData={chartData} title={title} subtitle={subtitle}/> : <BarChart chartData={chartData} title={title} subtitle={subtitle}/>}
        </S.TileBig>
    );
}
