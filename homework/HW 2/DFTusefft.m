%  xn是信号，n是坐标，N是点数
%  N =8;
%  n = [0:1:N-1];
%  xn = 0.5.^n;        % 指数信号
function [] = DFTusefft(xn,n,N)
    figure(1);
    Xk=fft(xn,N);      % 傅立叶变换
    subplot(211);
    stem(n,xn);
    title('原信号');

    subplot(212);
    stem(n,abs(Xk));
    title('FFT变换');
end