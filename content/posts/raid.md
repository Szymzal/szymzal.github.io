+++
title = "Raid"
date = "2023-05-03"
lastmod = "2023-05-09"
author = "Szymzal"
authorTwitter = "" #do not include @
cover = ""
tags = ["disks"]
keywords = ["disks"]
description = "Wszystko o RAID-ach i jak je tworzyć"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
+++
# Podstawy

RAID (*Redundant Array of Independent Disks*) sposób w jaki jest wykorzystywane miejsce na różnych dyskach.
Pozwala na współprace dysków między sobą, żeby zwiększyć bezpieczeństwo plików kiedy przykładowo jeden z dysków się uszkodził.

RAID występuje w kilku wersjach, gdzie każda z nich jest wykorzystywana do innych celów:
- RAID 0 -> Wszystko zostaje dzielone na proporcjonalne porcje, które są przechowywane na różnych dyskach. Dzięki temu prędkość zapisu często się zwiększa. Jednak w przypadku awarii jednego z dysków w RAID-dzie wsyszystkie dane są utracone.
- RAID 1 -> Tworzy idealną kopie drugiego dysku. Najbezpieczniejsza metoda odzyskiwania plików z uszkodzonego dysku.
- RAID 2 -> DOKOŃCZYĆ
- RAID 3 -> DOKOŃCZYĆ
- RAID 4 -> DOKOŃCZYĆ
- RAID 5 -> DOKOŃCZYĆ
- RAID 6 -> DOKOŃCZYĆ
- RAID 01 (0 + 1) -> DOKOŃCZYĆ
- RAID 10 (1 + 0) -> DOKOŃCZYĆ

# Tworzenie RAID-a

RAID można stworzyć w 2 różnych miejscach: 
- w BIOS/UEFI
- w OS

Tworzenie RAID-a w OS nazywane jest *Software RAID* i można go stworzyć w linuxie ([więcej informacji tutaj](../linux/md)).

Jest to też możliwe w Windows-ie jednak nie zajrzałem do tego i nie wiem na razie jak to zrobić.
