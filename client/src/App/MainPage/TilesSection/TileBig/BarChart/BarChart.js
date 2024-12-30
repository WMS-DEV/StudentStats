import React from "react";
import { Bar } from "react-chartjs-2";
import * as S from './styles';

export const BarChart = ({ chartData, title }) => {
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