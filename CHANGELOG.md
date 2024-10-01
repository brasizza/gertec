## 0.0.8
    - Inclusão de um processo em buffer, pois a impressao de QRcode e Barcode nao segue a mesma fila de impressao, e se vc mandasse tudo ao mesmo tempo, o texto as vezes imprimia no meio do qrcode
# Importante
    Para o app funcionar em release mode, vc precisa colocar isso no gradle, pois o aidl comprimido causa problemas quando ele compila falhando na busca de alguns métodos. (Se você manja de proguard, precisa adicionar as exceções sem precisar por esses falses)
```gradle
 buildTypes {
    release {
        signingConfig = signingConfigs.debug
        minifyEnabled false // <<False para minificacao >>
        shrinkResources false  // <<False para shrink >>
    }
}
```
    
    
## 0.0.7
    - Atualização do  SDK do SK210 (Não estou utilizando a easy-layer, e sim a SDK nativa do aparelho)

## 0.0.6
    - Melhorias no stub de leitura do qrcode/barcode

## 0.0.5
    - README fix e imagens
    - Atualização para receber suporte ao leitor de código de barras/ QrCode abaixo do dispositivo, somente ativo (mediante a execução para iniciar a captura)


## 0.0.4
 - Atualização para receber suporte ao leitor de código de barras/ QrCode abaixo do dispositivo, somente ativo (mediante a execução para iniciar a captura)

## 0.0.3
 - Correção do readme para colocar as imagens.

## 0.0.2
 - Formatação de código para o score do pub.dev

## 0.0.1

* Release do projeto inicial.
Implementação inicial e testada somente em ** SK210 **
- [x] Escreve uma linha ou um texto estilizado (tipos de estilo no final do readme) -  **printText**
- [x] Avança x linhas à sua escolha - **wrap**
- [x] Faz o corte de papel - **cutPaper**
- [x] Imprime códigos de barras de todos os estilos e modelos (tipos de modelos no final do readme) - **printBarCode**
- [x] Imprime qrcodes com todos os tipos de correções e tamanhos - **printQrcode**
- [x] Desenha uma linha com o caractere customizável para separar áreas de impressão  - **line**
- [x] Imprime uma imagem tanto vinda da web quanto de algum asset (ver exemplo) - **printImage**
- [x] Pega e status da impressora (tipos disponíveis no final do readme)



