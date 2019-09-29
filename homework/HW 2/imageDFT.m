function [] = imageDFT()
    I=imread('test.jpg');
    I=rgb2gray(I);
    I=im2double(I);
    [x,y] = size(I);
    ans = ones(x,y);
    com = 0+1i;
    for u =1:x
        for v= 1:y
            sn =0;
            for i=1:x                
                for j=1:y
                    sn = sn+I(i,j)*exp(-com*2*pi*(u*i/x+v*j/y));
                end
            end
            ans(u,v) = sn;
        end
    end
    F=fftshift(ans);
    F= abs(F);
    F=log(F+1);
    figure(5);
    imshow(F,[]);
end