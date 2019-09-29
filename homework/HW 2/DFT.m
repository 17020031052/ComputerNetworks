function [] = DFT(xn,n,N)
    Xk = zeros(1,N);    
    for k=1:N
        sn =0.0;
        for i=1:N
            sn = sn+xn(i)*exp(-j*2*pi*i*k/N);
        end
        Xk(k) = sn;
    end
    figure(2);
    subplot(211);
    stem(n,xn);
    title('Ô­ÐÅºÅ');

    subplot(212);
    stem(n,abs(Xk));
    title('DFT')
end