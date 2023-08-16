+++
title = "DRBL"
date = "2023-05-20"
lastmod = "2023-05-20"
author = "Szymzal"
authorTwitter = "" #do not include @
cover = ""
tags = []
keywords = []
description = "Zadanie z informatyki"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
draft = true
+++

# Przygotowanie 

Do wykonania tego zadania wykorzystane będzie Virutalbox 7.0.4 wraz z 3 systemami:

- Ubuntu 22.04 -> na którym będzie serwer DHCP
- Ubuntu 22.04 -> na którym będzie serwer DRBL
- Windows 10 -> będzie klientem DRBL-a

W tym opisie zostaną pominięte instalacje systemów operacyjnych na wirtualnych maszynach

# Serwer DHCP

## Ustawienie statycznego adresu IP

Ubuntu używa pakietu `netplan` jako narzędzia konfiguracyjnego interfejsów.
Aby ustawić statyczny adres IP musimy zmienić jego konfigurację,
która występuje w `/etc/netplan/00-installer-config.yaml`.

Zanim się to zrobi należy dobrze spojrzeć na wynik polecenia `ip a`,
żeby zobaczyć jaką nazwę ma nasz interfejs (Ten co nie ma żadnego przypisanego adresu IP). 
W tym przypadku nosi nazwę `enp0s8`.

W konfiguracji netplan-a pod nazwą naszego interfejsu (`enp0s8`) dodajemy statyczny adres IP:
```yaml
enp0s8:
  addresses:
  - 172.16.0.1/24
```

Aby zatwierdzić zmiany konfiguracji możemy użyć komendy:
`sudo netplan try`

Aby zobaczyć czy nasz interfejs otrzymał wpisany adres statyczny używamy polecenia `ip a`.

## Instalacja serwera DHCP

Ubuntu jest na podstawie Debian-a, który używa menadżera pakietów `apt`.
Więc, żeby coś zainstalować używamy komendy: 

`sudo apt install <nazwa-pakietu>`

Ale zawsze warto zaktualizować repozytoria z których informacje o aktualizacjach i same pakiety są instalowane.
Dlatego, kiedy chcemy zainstalować serwera DHCP używamy komendy:

`sudo apt update && sudo apt install isc-dhcp-server`

Tutaj `&&` używa się kiedy chcemy łączyć 2 komendy w jedną.

## Konfiguracja serwera DHCP

Konfiguracja serwera DHCP występuje w `/etc/dhcp/dhcpd.conf`.

Ponieważ nie potrzebujemy bardzo wymagającej konfiguracji możemy skorzystać z gotowych 
przykładów w konfiguracji.

Szukamy tego miejsca:

```
# A slightly different configuration for an interanal subnet.
#subnet 10.5.5.0 netmask 255.255.255.224 {
#   range 10.5.5.26 10.5.5.30;
#   option domain-name-servers ns1.internal.example.org;
#   option domain-name "internal.example.org";
#   option routers 10.5.5.1;
#   option broadcast-address 10.5.5.31;
#   default-lease-time 600;
#   max-lease-time 7200;
#}
```

Odkomentujemy go i zmieniamy parametry:

```
# A slightly different configuration for an interanal subnet.
subnet 172.16.0.0 netmask 255.255.255.0 {
   range 172.16.0.5 172.16.0.50;
   option domain-name-servers 172.16.0.1;
   option routers 172.16.0.1;
   option broadcast-address 172.16.0.255;
}
```

Teraz, żeby zaakceptować zmiany wykonujemy tą komendę:

`sudo systemctl restart isc-dhcp-server`

oraz możemy sprawdzić czy nie błędów korzystając z tej komendy:

`sudo systemctl status isc-dhcp-server`

Warto ograniczyć na jakie interfejsy serwer DHCP działa.
Możemy to zrobić w `/etc/default/isc-dhcp-server`.
W `INTERFACESv4` wpisujemy nazwę naszego interfejsu na którym będzie działać serwer DHCP.

Dla pewności warto uruchomić ponownie serwer DHCP.

# DRBL

## Ustawienie statycznego adresu IP

Ponieważ nie chcemy co jakiś czas, żeby adres naszego serwera DRBL się zmieniał ustawimy adres statyczny.
W tym przypadku, zrobimy to w konfiguracji serwera DHCP. 
Najpierw jednak potrzebujemy adres MAC interfejsu na sewerze DRBL.
Możemy go znaleźć w wyniku komendy: `ip a`.

Kiedy mamy już adres MAC interfejsu na serwerze DHCP w jego konfiguracji znajdujemy taki fragment:

```
# Fixed IP addresses can also be specified for hosts.   These addresses
# should not also be listed as being available for dynamic assignment.
# Hosts for which fixed IP addresses have been specified can boot using
# BOOTP or DHCP.    Hosts for which no fixed address range on the subnet
# to which a BOOTP client is connected which has the dynamic-bootp flag
# set.
#host fantasia {
#   hardware ethernet 08:00:07:26:c0:a5;
#   fixed-address fantasia.example.com;
#}
```

Odkomentujemy i zmieniamy na:
```
# Fixed IP addresses can also be specified for hosts.   These addresses
# should not also be listed as being available for dynamic assignment.
# Hosts for which fixed IP addresses have been specified can boot using
# BOOTP or DHCP.    Hosts for which no fixed address range on the subnet
# to which a BOOTP client is connected which has the dynamic-bootp flag
# set.
host fantasia {
   hardware ethernet <adres MAC interfejsu>;
   fixed-address 172.16.0.2;
}
```

Aktualizujemy zmiany restartując usługę:

`sudo systemctl restart isc-dhcp-server`

Teraz wracając na serwer DRBL, aktualizujemy adres IP aktualizując zmiany, czyli:

`sudo netplan apply`

## Instalacja DRBL

Zanim się zainstaluje DRBL-a trzeba dodać repozytorium i klucz do apt.
Najpierw komenda do dodania klucza repozytorium do apt:

`wget https://drbl.org/GPG-KEY-DRBL -O- | gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/GPG-KEY-DRBL.gpg`

Teraz dodajemy nowe repozytorium do `/etc/apt/sources.list`:

`deb http://free.nchc.org.tw/drbl-core drbl stable`

I aktualizujemy repozytoria:

`sudo apt update`

Teraz możemy zainstalować DRBL:

`sudo apt install drbl`

Aby zakończyć instalację i rozpoczyć konfigurację DRBL używamy komendy:

`sudo drblsrv -i`

Wszędzie wpisujemy "N", jedynie w pytaniu o aktualizacji wpisujemy: "T".
Może pytać o z jakiego repozytorium pobrać kernela, w takim wypadku wybieramy z apt.

Teraz zaczynamy konfigurację DRBL-a używając komendy:

`sudo drblpush -i`

Podajemy nazwę domeny: egzamin.local

Oraz podajemy nazwę serwera w domenie: drblserver

Prefix pozostawiamy taki sam.

Wybieramy interfejs, który będzie używany do komunikacji z Internetem. W tym wypadku jest to enp0s3.

Wybieramy opcję "N", żeby nie zbierać adresów MAC klientów.

Tutaj możemy sobie wybrać jakikolwiek chcemy zakres. Ja wybrałem od 172.16.0.5 do 172.16.0.49

Wybrałem tryb Full DRBL oraz Full Clonezilla.

Wybieramy miejsce zapisu obrazów systemów. Upewnić się, że są one zapisywane na 2 dysku.

Wybieramy opcję domyślną dla partycji swap oraz jej wielkości.

Wybieramy opcję domyślną także dla trybu do którego będą się uruchamiać klienci.

Ustawiamy domyślne logowanie.

Chcemy zmienić hasło roota dla klientów.

Ale nie dodajmy hasła, żeby za każdym razem kiedy się uruchamiali musieli podać hasło.

Ustawiamy domyślą długość komunikatu dla wyboru obrazu systemu dla klientów.

Pozostawiamy domyślne ustawienia dla tła, peryferii, podwójnych adresów IP, trybu konsoli oraz serweru NAT.

Po tym wszyskim możemy w końcu podłączyć się klientem do serwera DRBL używając PXE.
W VirtualBox-ie można to zrobić na 2 sposoby:
- Przy uruchomieniu klikamy F12 i wybieramy: LAN
- Zmieniamy kolejność uruchamiania, gdzie sieć jest na samej górze

Ja użyje tutaj 2 sposób.

## Tworzenie klona dysku klienta

Warto już wcześniej, żeby nie było zaskoczeń, ustawić adres statyczny na klienta, możemy to zrobić zmieniając konfigurację serwera DHCP, który znajduje się na serwerze DRBL.

Teraz wracamy do serwera DRBL i wpisujemy komendę:
`sudo dcs`

Wybieramy interfejsy, które nas interesują, w tym wypadku wybieram tylko jeden: 172.16.0.6;

Wybieramy "clonezilla-start" -> Beginner -> "save-disk" -> "Now_in_server" -> podajemy nazwę obrazu -> podajemy nazwę dysku (zgodnie z opisami Linux-owymi) -> pomijamy sprawdzanie dysku -> "-p poweroff" -> "-z1p" -> "0"

Teraz uruchamiamy do DRBL klienta i wybieramy opcję: "Clonezilla".

Tak oto zaczyna się proces kopiowania dysku na serwer.
