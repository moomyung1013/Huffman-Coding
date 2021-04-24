# Huffman Coding
 
---
title: 허프만 코딩(Huffman Coding)
categories: [project]
comments: true
---

# 개요   
허프만 코딩(Huffman Coding)은 자료 압축의 가장 오래되고 기초적인 방법 중의 하나이며 최소 중복 코딩(minimum redundancy coding)에 기반한 알고리즘을 사용한다. 최소 중복 코딩은 문자들이 자료 집합에서 얼마나 자주 발생하는지를 안다면 발생되는 문자들의 비율에 따라 자주 반복되는 문자들을 더 적은 비트로 부호화 하는 방법이다. 일반적인 경우 한 문자를 표현하기 위해 한 개의 바이트(ASCII 코드)를 사용하나 허프만 코드는 문자 발생 비율에 따라 다른 크기의 비트로 표현한다.   

### 제한조건   
1. 허프만 코드로 압축한 파일은 이진 파일로 저장한다.
1. 허프만 코드로 압축한 파일은 허프만 테이블과 허프만 부호화 코드를 포함한다.
1. 50,000자, 100,000자, 500,000자 이상의 텍스트 파일을 준비하여 테스트한다.

### 확장기능   
1. ASCII 문자 이외의 한글, 일본어와 같은 유니코드에서도 프로그램이 동작하도록 한다.
1. 압축률, 압축 시간, 복원 시간의 통계정보를 계산하여 출력하도록 한다.
1. 압축 진행 후, 빈도수를 알고 싶은 문자를 입력하면 해당 문자의 빈도수를 출력하도록 한다.

<br><br><br>
# 개발 목표   
임의의 텍스트 파일(.txt)이 주어지면 허프만 코딩 방법을 이용하여 자료를 압축(.txt(HuffZip))하며, 반대로 압축된 파일(.txt(HuffZip))이 주어지면 파일을 압축 해제(.txt(HuffZip)(unzip))하는 프로그램을 작성한다.   

### 개발 도구   
**JAVA-Eclipse IDE 2019-06(jdk1.8.0_211, jre1.8.0_211)**

<br><br><br>
# 자료구조  
- **Node.class**: 문자 노드의 데이터를 저장하는 클래스

|변수명|자료형|설명|
|:---:|:---:|:---:|
|ch|`char`|문자를 저장하는 데이터 ex) 'A'|
|fq|`int`|문자의 빈도수|
|asciiCode|`int`|해당 문자의 10진수 정수 값|
|str|`String`|문자를 문자열 형태로 저장 ex) "A"|
|Code|`String`|문자의 허프만 코드 값|
|leftNode|`Node`|문자 노드의 왼쪽 자식 노드|
|rightNode|`Node`|문자 노드의 오른쪽 자식 노드|
|parent|`Node`|문자 노드의 부모 노드|

<br><br>
- **FileRead.class**: 입력 파일의 정보를 읽는 클래스

|변수명|자료형|설명|
|:---:|:---:|:---:|
|filename|`String`|파일 이름|
|str|`String`|원본 텍스트 파일의 내용|
|decode|`String`|부호화된 파일을 복호화하여 저장|
|ReadLine|`String`|압축된 파일이 입력될 시, 압축 파일을 한줄 씩 읽어 저장|
|bitSize|`String`|텍스트 파일을 이진 파일로 저장할 때, 코드 테이블을 먼저 저장한 뒤, 테이블의 끝임을 알리는 구분 문자열을 넣고, 부호화된 문자열의 길이를 저장하는데. 이 값을 읽어와 저장하는 변수|
|codeBit|`String`|읽어온 비트 값을 차례대로 저장|
|originData|`String`|읽어온 이진 파일의 부호화된 문자열을 저장|
|node|`ArrayList<Node>`|인코딩을 위한 파일을 불러올 때, 파일을 읽으면서 문자 노드들을 저장|
|codeTable|`ArrayList<Node>`|이진 파일을 읽어와서 코드 테이블 정보로 저장|
|ch|`int`|인코딩 시, 텍스트 파일을 읽음 ex) ch=fread.read()|
|buffer|`int`|디코딩할 파일에서 읽어온 부호화된 데이터의 정수를 저장|
|bit|`int`|buffer 값을 비트로 표현|
|bitCount|`int`|비트가 이동한 횟수|
|fq|`int []`|문자의 빈도수|
|startTime|`long`|인코딩 시작 시간|
|file|`File`|filename의 파일 변수|
|bufRead|`BufferedReader`|filename을 읽는 파일 변수|

<br><br>
- **FileWrite.class**: 파일을 저장하는 클래스

|변수명|자료형|설명|
|:---:|:---:|:---:|
|fis|`FileWriter`|fis=new FileWriter(filename)|
|bou|`BufferedWriter`|bou=new BufferedWriter(fis)|
|bitCount|`int`|비트가 이동한 횟수|
|buffer|`int`|텍스트 파일을 비트 연산자로 계산하여 1바이트 단위로 저장하기 위한 변수|

<br><br>
- **HuffmanTree.class**: 허프만 트리 생성을 위한 클래스

|변수명|자료형|설명|
|:---:|:---:|:---:|
|node|`ArrayList<Node>`|Encode 객체로부터 노드 정보를 받아옴|
|pq|`PQueue`|허프만 트리를 만들 때 필요한 우선순위에 따라 정렬된 우선순위 큐 객체|
|NewNode|`Node`|부모 노드를 만들기 위한 노드|

<br><br>
- **Encode.class**: 압축을 수행하는 클래스

|변수명|자료형|설명|
|:---:|:---:|:---:|
|fw|`FileWrite`|텍스트 파일의 부호화가 완료되면 파일저장을 위해 선언되는 객체|
|HufTree|`HuffmanTree`|허프만 트리를 구성|
|filename|`String`|파일 이름|
|CodeStr|`String`|부호화 한 문자열|
|NameAndCode|`String`|GUI로 코드 테이블 정보를 출력하기 위해 문자와 해당하는 허프만 코드를 저장|
|node|`ArrayList<Node>`|텍스트 파일의 노드 정보|
|bitSize|`int`|부호화된 문자열의 크기|

<br><br>
- **Decode.class**: 압축 풀기를 수행하는 클래스

|변수명|자료형|설명|
|:---:|:---:|:---:|
|fr|`FileRead`|읽은 파일의 정보|
|fw|`FileWrite`|복호화 한 데이터를 파일로 저장하기 위한 객체|
|decode|`String`|복호화한 데이터 내용|
|originData|`String`|부호화된 이진 파일 내용|

<br><br><br>
# 알고리즘   
1. 텍스트 파일이 입력되면, 빈도수와 문자 데이터를 읽어 노드를 생성한다.   

```
ch <- fread.read()
/*파일의 끝까지 읽어 원본 문자열 저장&빈도수 계산*/
while ch≠-1
	str <- str + (char) ch
	fq[ch]++
	if ch=10
		fq[ch] <- 0
	ch <- fread.read()
/*노드 생성*/
for : j가 fq의 길이보다 작은 동안
	if fq[j]>0
		node.add(new Node((char)j, fq[j]))
```   


2. 허프만 트리를 구성한다.   
```
pq <- new PQueue	//우선순위 큐 생성

for : 노드의 개수만큼 반복
	pq.enqueue(node)
index <- pq.size()	//큐의 크기만큼 지정

/* 트리 생성 */
for : i가 (index X 2)-1 보다 작은 동안
	a <- pq.peek()
	b <- pq.peek()
	NewNode <- new Node(a.ch+b.ch, a.fq+b.fq)
	NewNode.leftNode <- a
	NewNode.rightNode <- b
	a.parent <- NewNode
	b.parent <- NewNode
	if : pq가 비어있지 않다면
		pq.euqueue(NewNode)
	else : pq가 비어있다면
		break
```   


3. 허프만 코드를 부여한다.   
```
parentNode <- node.parent
temp <- node

while : parentNode가 null이 아닐동안
	if : temp가 parentNode의 왼쪽 자식이면
		code <- '0'+code
	else if : temp가 parentNode의 오른쪽 자식이면
		code <- '1'+code
	temp <- temp.parent
	parentNode <- parentNode.parent
node.Code <- code
```   


4. 이진 파일로 저장한다.   
```
/* 코트 테이블을 작성한다 */
public void TableWrite(ArrayList<Node> node, int bitSize){
  for : 노드의 크기만큼 반복
  	if : node의 아스키코드 정수가 13이라면
  		write("\r\n")
  	else : 그 외 다른 문자의 경우
  		write(node.get(i).asciiCode)
  	write(':')	//문자와 코드의 구분자
  	write(node.get(i).Code)	//코드

  write("123\n")	//코드 테이블의 끝임을 알리는 문자열
  write(bitSize)	//부호화한 문자열의 길이
}
public void StringToChar(String Code){
  /* 문자열을 문자로 변환하여 비트 계산을 한다 */
  for : 부호화한 문자열의 길이만큼 반복
  	CharToBit(Code.charAt(i))
  	if : i=Code.length()-1
  		writeBit()
  		break
}
public void CharToBit(char code){
  /* 문자를 비트 계산한다 */
  if : 문자가 1인경우
  	buffer <- buffer OR (64>>>bitCount)
  bitCount++
  if : 비트 이동횟수가 7번이 되면
  	writeBit()	//buffer를 파일에 저장
}
public void writeBit(){
  /* 파일에 저장한다 */
  if : 비트가 한번도 이동하지 않았다면
  	return
  write(buffer)
  buffer <- 0
  bitCount <- 0
  return
}
```   


5. 복호화한다.   
```
bit <- readBit() //비트를 읽어 2진수로 변환한다 ex)101...
for : buffer 가 -1이 아닐동안
	Iterator<Node> it <- codeTable.iterator()
	if : 이진 파일로 전달받은 부호화 문자열 길이와 현재 디코딩 중인 문자열 길이가 같다면
		break
	codeBit <- codeBit+bit
	while : 노드의 수만큼 반복
		if : codeBit와 노드의 허프만 코드가 같다면
			decode <- decode+노드의 문자
			codeBit <- ""
			break
	bit <- readBit()
```   

<br><br>
### 자세한 소스코드를 보고 싶다면 [허프만 코딩 Github][huff-git] 을 확인.

[huff-git]: https://github.com/moomyung1013/Level3_Huffman
