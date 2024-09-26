package ch.opentransportdata.presentation.utils

import ch.opentransportdata.R
import ch.opentransportdata.ojp.data.dto.response.tir.leg.AttributeDto

/**
 * Created by Michael Ruppen on 26.09.2024
 */
fun AttributeDto.isAttributeInfo(): Boolean {
    return this.code.startsWith("A")
}

fun AttributeDto.iconMappingKey(): String? {
    return if (this.isAttributeInfo()) this.code.substringAfterLast('_') else null
}

fun AttributeDto.icon(): Int? {
    return when (this.iconMappingKey()) {
        "1" -> R.drawable.sa_1
        "2" -> R.drawable.sa_2
        "AA" -> R.drawable.sa_aa
        "ABTEILKINDERWAGEN" -> R.drawable.sa_abteilkinderwagen
        "AF" -> R.drawable.sa_af
        "AR" -> R.drawable.sa_ar
        "AT" -> R.drawable.sa_at
        "AW" -> R.drawable.sa_aw
        "B" -> R.drawable.sa_b
        "BB" -> R.drawable.sa_bb
        "BE" -> R.drawable.sa_be
        "BI" -> R.drawable.sa_bi
        "BK" -> R.drawable.sa_bk
        "BL" -> R.drawable.sa_bl
        "BR" -> R.drawable.sa_br
        "BS" -> R.drawable.sa_bs
        "BV" -> R.drawable.sa_bv
        "BZ" -> R.drawable.sa_bz
        "CC" -> R.drawable.sa_cc
        "CI" -> R.drawable.sa_ci
        "CO" -> R.drawable.sa_co
        "DS" -> R.drawable.sa_ds
        "DZ" -> R.drawable.sa_dz
        "EP" -> R.drawable.sa_ep
        "FA" -> R.drawable.sa_fa
        "FL" -> R.drawable.sa_fl
        "FS" -> R.drawable.sa_fs
        "FW" -> R.drawable.sa_fw
        "FZ" -> R.drawable.sa_fz
        "GK" -> R.drawable.sa_gk
        "GL" -> R.drawable.sa_gl
        "GN" -> R.drawable.sa_gn
        "GP" -> R.drawable.sa_gp
        "GR" -> R.drawable.sa_gr
        "GX" -> R.drawable.sa_gx
        "GZ" -> R.drawable.sa_gz
        "HI" -> R.drawable.sa_hi
        "HK" -> R.drawable.sa_hk
        "HL" -> R.drawable.sa_hl
        "HN" -> R.drawable.sa_hn
        "HT" -> R.drawable.sa_ht
        "II" -> R.drawable.sa_ii
        "JE" -> R.drawable.sa_je
        "KB" -> R.drawable.sa_kb
        "KW" -> R.drawable.sa_kw
        "LC" -> R.drawable.sa_lc
        "LE" -> R.drawable.sa_le
        "ME" -> R.drawable.sa_me
        "MI" -> R.drawable.sa_mi
        "MO" -> R.drawable.sa_mo
        "MP" -> R.drawable.sa_mp
        "NF" -> R.drawable.sa_nf
        "NJ" -> R.drawable.sa_nj
        "NV" -> R.drawable.sa_nv
        "OB" -> R.drawable.sa_ob
        "OM" -> R.drawable.sa_om
        "P" -> R.drawable.sa_p
        "PA" -> R.drawable.sa_pa
        "PG" -> R.drawable.sa_pg
        "PH" -> R.drawable.sa_ph
        "PL" -> R.drawable.sa_pl
        "PR" -> R.drawable.sa_pr
        "R" -> R.drawable.sa_r
        "RA" -> R.drawable.sa_ra
        "RB" -> R.drawable.sa_rb
        "RC" -> R.drawable.sa_rc
        "RE" -> R.drawable.sa_re
        "REISEGRUPPE" -> R.drawable.sa_reisegruppe
        "RN" -> R.drawable.sa_rn
        "RP" -> R.drawable.sa_rp
        "RQ" -> R.drawable.sa_rq
        "RR" -> R.drawable.sa_rr
        "RS" -> R.drawable.sa_rs
        "RT" -> R.drawable.sa_rt
        "RU" -> R.drawable.sa_ru
        "RY" -> R.drawable.sa_ry
        "RZ" -> R.drawable.sa_rz
        "S" -> R.drawable.sa_s
        "SB" -> R.drawable.sa_sb
        "SC" -> R.drawable.sa_sc
        "SD" -> R.drawable.sa_sd
        "SF" -> R.drawable.sa_sf
        "SH" -> R.drawable.sa_sh
        "SK" -> R.drawable.sa_sk
        "SL" -> R.drawable.sa_sl
        "SM" -> R.drawable.sa_sm
        "SN" -> R.drawable.sa_sn
        "SP" -> R.drawable.sa_sp
        "SV" -> R.drawable.sa_sv
        "SZ" -> R.drawable.sa_sz
        "TA" -> R.drawable.sa_ta
        "TC" -> R.drawable.sa_tc
        "TD" -> R.drawable.sa_td
        "TF" -> R.drawable.sa_tf
        "TG" -> R.drawable.sa_tg
        "TK" -> R.drawable.sa_tk
        "TN" -> R.drawable.sa_tn
        "TS" -> R.drawable.sa_ts
        "TT" -> R.drawable.sa_tt
        "TX" -> R.drawable.sa_tx
        "UK" -> R.drawable.sa_uk
        "VB" -> R.drawable.sa_vb
        "VC" -> R.drawable.sa_vc
        "VI" -> R.drawable.sa_vi
        "VK" -> R.drawable.sa_vk
        "VL" -> R.drawable.sa_vl
        "VN" -> R.drawable.sa_vn
        "VO" -> R.drawable.sa_vo
        "VP" -> R.drawable.sa_vp
        "VR" -> R.drawable.sa_vr
        "VS" -> R.drawable.sa_vs
        "VT" -> R.drawable.sa_vt
        "VX" -> R.drawable.sa_vx
        "WB" -> R.drawable.sa_wb
        "WF" -> R.drawable.sa_wf
        "WL" -> R.drawable.sa_wl
        "WR" -> R.drawable.sa_wr
        "WS" -> R.drawable.sa_ws
        "WT" -> R.drawable.sa_wt
        "WV" -> R.drawable.sa_wv
        "X" -> R.drawable.sa_x
        "XP" -> R.drawable.sa_xp
        "XR" -> R.drawable.sa_xr
        "XT" -> R.drawable.sa_xt
        "XX" -> R.drawable.sa_xx
        "Y" -> R.drawable.sa_y
        "YB" -> R.drawable.sa_yb
        "YM" -> R.drawable.sa_ym
        "YT" -> R.drawable.sa_yt
        "Z" -> R.drawable.sa_z
        "ZM" -> R.drawable.sa_zm
        else -> null
    }
}