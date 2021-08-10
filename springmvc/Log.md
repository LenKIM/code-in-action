쓸모없는 리소스를 사용하게 됩니다.

log.debug("data="+data)
 - 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다. 결과적으로 문자 더하기 연산이 발생한다.

log.debug("data={}", data)