import React from "react";
import { Line } from "react-chartjs-2";
import * as S from './styles';



export const LineChart = ({ chartData, title, subtitle }) => {
  const smallerText = {
    'font-size': '2.3vh'
  };

  const shouldTextBeSmaller = (text) => {
    return text.length > 45 && window.screen.width < 1024;
  };

  return (
    <S.LineChart>
      <S.Heading style={shouldTextBeSmaller(title) ? smallerText : null}>{title}</S.Heading>
      <S.Subheading>{subtitle}</S.Subheading>
      <S.Line>
        <Line
          data={chartData}
          options={{
            scales: {
              x: {
                ticks: {
                  callback: function(value) {
                    if (window.innerWidth <= 778 || (window.innerWidth <= 1300 && window.innerWidth >= 1000)) {
                      if (chartData.labels[value].length >= 17) {
                        return chartData.labels[value].slice(0, 7) + '...';
                      }
                    }
                    return chartData.labels[value]
                  }
                }
              }
            },
            plugins: {
              legend: {
                display: false
              },
              tooltip: {
                callbacks: {
                  title: function(value) {
                    return chartData.labels[value];
                  }
                }
              }
            },
            layout: {
              padding: 20
            },
              maintainAspectRatio: false,
          }}
        />
      </S.Line>
    </S.LineChart>
  );
}

