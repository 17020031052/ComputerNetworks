%  xn���źţ�n�����꣬N�ǵ���
%  N =8;
%  n = [0:1:N-1];
%  xn = 0.5.^n;        % ָ���ź�
function [] = DFTusefft(xn,n,N)
    figure(1);
    Xk=fft(xn,N);      % ����Ҷ�任
    subplot(211);
    stem(n,xn);
    title('ԭ�ź�');

    subplot(212);
    stem(n,abs(Xk));
    title('FFT�任');
end