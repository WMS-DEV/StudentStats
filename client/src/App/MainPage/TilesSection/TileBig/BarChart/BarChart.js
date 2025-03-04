import React from "react";
import { Bar } from "react-chartjs-2";
import * as S from './styles';

export const BarChart = ({ chartData, title }) => {

  const fractionValue = Math.max(...chartData.datasets[0].data) * 0.1;
  
  const minYvalue = Math.max(0, Math.floor(Math.min(...chartData.datasets[0].data) - fractionValue));  

  return (
    <S.BarChart>
      <S.Heading>{title}</S.Heading>
      <S.Bar>
        <Bar
          data={chartData}
          options={{
            plugins: {
              title: {
                display: true,
              },
              legend: {
                display: false
              }
            },
            layout: {
              padding: 20
            },
            maintainAspectRatio: false,
            scales: {
              y: {
                min: minYvalue,
                ticks: {
                  color: '#FFFAE9',
                }
              },
              x: {
                ticks: {
                  color: '#FFFAE9',
                }
              },
            }
          }}
        />
      </S.Bar>
    </S.BarChart>
  );
};